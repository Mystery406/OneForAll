package com.dev.l.oneforall.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.dev.l.oneforall.R;
import com.dev.l.oneforall.fragment.AboutFragment;
import com.dev.l.oneforall.fragment.OCRFragment;
import com.dev.l.oneforall.fragment.UnsplashFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;

    private Fragment currentFragment, aboutFragment, ocrFragment, unsplashFragment;
    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        fm = getSupportFragmentManager();
        aboutFragment = new AboutFragment();
        ocrFragment = new OCRFragment();
        unsplashFragment = new UnsplashFragment();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_ocr) {
            switchContent(ocrFragment);
            toolbar.setTitle("文字识别");
        } else if (id == R.id.nav_gallery) {
            switchContent(unsplashFragment);
            toolbar.setTitle("看图");
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_about) {
            switchContent(aboutFragment);
            toolbar.setTitle("关于");
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * 切换fragment
     *
     * @param fragment
     */
    private void switchContent(Fragment fragment) {
        if (currentFragment != fragment) {
            if (!fragment.isAdded()) {
                // 如果目标fragment没有被add则增加一个fragment
                if (currentFragment != null) {
                    fm.beginTransaction()
                            .hide(currentFragment)
                            .commit();
                }
                fm.beginTransaction()
                        .add(R.id.content_main, fragment)
                        .commit();
            } else {
                // 隐藏当前的fragment，显示下一个
                fm.beginTransaction()
                        .hide(currentFragment)
                        .show(fragment)
                        .commit();
            }
            currentFragment = fragment;
        }
    }
}
