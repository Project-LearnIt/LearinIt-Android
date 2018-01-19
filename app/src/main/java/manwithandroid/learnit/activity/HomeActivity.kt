package manwithandroid.learnit.activity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar_home.*
import kotlinx.android.synthetic.main.content_home.*
import kotlinx.android.synthetic.main.nev_home.*
import manwithandroid.learnit.R
import manwithandroid.learnit.adapters.ClassesAdapter
import manwithandroid.learnit.dialogs.JoinClassDialog
import manwithandroid.learnit.helpers.LessonsBuilderHelper
import manwithandroid.learnit.helpers.UserHelper

/**
 * Created by roi-amiel on 12/28/17.
 */
class HomeActivity : AppCompatActivity(), OnNavigationItemSelectedListener {

    /* Adapters */
    private val classesAdapter = ClassesAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)

        supportActionBar?.title = UserHelper.getConnectedUser()?.name

        val toggle = ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.abc_capital_off, R.string.abc_capital_on)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navigationView.setNavigationItemSelectedListener(this)

        joinClassFAB.setOnClickListener {
            val joinClassDialog = JoinClassDialog(this)
            joinClassDialog.setOnCancelListener({
                onResume()
            })
            joinClassDialog.show()
        }

        // Install recyclers views
        classesRecyclerView.layoutManager = LinearLayoutManager(this)
        classesRecyclerView.setHasFixedSize(true)
        classesRecyclerView.adapter = classesAdapter
    }

    override fun onResume() {
        super.onResume()
        // Check if need to create profile for this user
        if (UserHelper.getConnectedUser()?.profile == null) {
            startActivity(CreateProfileActivity.createIntent(this, {
                if (it == null) {
                    Toast.makeText(this, R.string.you_must_create_profile, Toast.LENGTH_SHORT).show()

                } else {
                    UserHelper.getConnectedUser()?.profile = it

                    UserHelper.updateUser {
                        if (!it.isSuccessful) throw RuntimeException("Can't update the user profile to server ${it.message}")
                    }
                }
            }))
        }

        LessonsBuilderHelper.updateBuildTask()

        // Update lists
        val connectedUser = UserHelper.getConnectedUser()!!

        val hasWeekLessons = false
        val hasUncompletedLessons = false
        val hasClasses = connectedUser.classes != null && (connectedUser.classes!!.size > 0)

        if (hasClasses) {
            classesAdapter.updateClassesList(connectedUser.classes!!)
        }

        weekLessonsCardView.visibility = if (hasWeekLessons) View.VISIBLE else View.GONE
        weekLessonsRecyclerView.visibility = if (hasWeekLessons) View.VISIBLE else View.GONE
        weekLessonsTextView.visibility = if (hasWeekLessons) View.VISIBLE else View.GONE

        uncompletedLessonsCardView.visibility = if (hasUncompletedLessons) View.VISIBLE else View.GONE
        uncompletedLessonsRecyclerView.visibility = if (hasUncompletedLessons) View.VISIBLE else View.GONE
        uncompletedLessonsTextView.visibility = if (hasUncompletedLessons) View.VISIBLE else View.GONE

        classesCardView.visibility = if (hasClasses) View.VISIBLE else View.GONE
        classesRecyclerView.visibility = if (hasClasses) View.VISIBLE else View.GONE
        classesTextView.visibility = if (hasClasses) View.VISIBLE else View.GONE

        noClassesPlaceholder.visibility = if (!hasWeekLessons && !hasUncompletedLessons && !hasClasses) View.VISIBLE else View.GONE
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        // menuInflater.inflate(R.menu.main, menu)
        nameTextView.text = UserHelper.getConnectedUser()?.name
        emailTextView.text = UserHelper.getConnectedUser()?.email

        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
        //R.key.settingsNevButton ->
        //R.key.aboutNevButton ->
            R.id.signoutNevButton -> {
                UserHelper.signOut()
                startActivity(Intent(this, SignInActivity::class.java))
                finish()
            }
        }

        return true
    }
}
