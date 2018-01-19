@file:Suppress("DEPRECATION")

package manwithandroid.learnit.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.ViewPager.OnPageChangeListener
import android.support.v7.app.ActionBar
import android.support.v7.app.ActionBar.NAVIGATION_MODE_TABS
import android.support.v7.app.ActionBar.TabListener
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_signin.*
import manwithandroid.learnit.R
import manwithandroid.learnit.fragments.SignInFragment
import manwithandroid.learnit.fragments.SignUpFragment
import manwithandroid.learnit.helpers.UserHelper

/**
 * Created by roi-amiel on 1/5/18.
 */
class SignInActivity : AppCompatActivity(), TabListener, OnPageChangeListener {
    companion object {
        /* Fragments */
        val fragments: Array<Fragment> = arrayOf(
                SignInFragment(),
                SignUpFragment()
        )

        val fragmentsNames: Array<Int> = arrayOf(
                R.string.sign_in_title,
                R.string.sign_up_title
        )
    }

    /* Adapter */
    private val pagerAdapter = SignInPagerAdapter(this, supportFragmentManager)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.navigationMode = NAVIGATION_MODE_TABS

        supportActionBar?.setTitle(R.string.welcome_message)

        viewpager.adapter = pagerAdapter

        viewpager.addOnPageChangeListener(this)

        fragmentsNames.forEach { supportActionBar?.addTab(supportActionBar?.newTab()?.setText(it)?.setTabListener(this)) }
    }

    override fun onResume() {
        if (UserHelper.isConnectedUser()) {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }

        super.onResume()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> onBackPressed()
        }

        return true
    }

    override fun onTabReselected(tab: ActionBar.Tab?, ft: FragmentTransaction?) {

    }

    override fun onTabUnselected(tab: ActionBar.Tab?, ft: FragmentTransaction?) {
    }

    override fun onTabSelected(tab: ActionBar.Tab?, ft: FragmentTransaction?) {
        if (tab != null) {
            viewpager.currentItem = tab.position
        }
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        supportActionBar?.setSelectedNavigationItem(position)
    }

    override fun startActivity(intent: Intent?) {
        super.startActivity(intent)
        finish()
    }

    class SignInPagerAdapter(private val context: Context, fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

        override fun getItem(position: Int) = fragments[position]

        override fun getCount() = fragments.size

        override fun getPageTitle(position: Int) = context.getString(fragmentsNames[position])

    }
}
