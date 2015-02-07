package com.example.suraj.popularphotos;

import android.media.Image;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;


public class PopularPhotoActivity extends ActionBarActivity {
    public static final String baseURL = "https://api.instagram.com/v1/media/popular?client_id=";
    public static final String clientID = "376f56d3928b4b16bcb0cfae5a62fca0";

    private ArrayList<Photo> photoList;
    private ListView photoListView;
    private PhotoAdapter photoAdapter;
    private PullToRefreshLayout pullToRefreshLayout;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_photo);

        // Initialize the variables
        initMemberVariables();
        setupPhotoListAdapterAndListeners();
        fetchPopularPhotos();
    }

    private void initMemberVariables() {
        photoListView = (ListView)findViewById(R.id.lvPhotos);
        progressBar = (ProgressBar)findViewById(R.id.pvLoading);
        pullToRefreshLayout = (PullToRefreshLayout) findViewById(R.id.ptr_layout);
        photoList = new ArrayList<Photo>();
        photoAdapter = new PhotoAdapter(this, photoList);
    }


    private void setupPhotoListAdapterAndListeners() {
        photoListView.setAdapter(photoAdapter);

        // Now setup the PullToRefreshLayout
        ActionBarPullToRefresh.from(this)
                // Mark All Children as pullable
                .allChildrenArePullable()
                // Set a OnRefreshListener
                .listener(new OnRefreshListener() {
                    @Override
                    public void onRefreshStarted(View view) {
                        fetchPopularPhotos();
                        pullToRefreshLayout.setRefreshComplete();
                    }
                })
                // Finally commit the setup to our PullToRefreshLayout
                .setup(pullToRefreshLayout);

        photoListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            int scrollState = AbsListView.OnScrollListener.SCROLL_STATE_IDLE;
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                this.scrollState = scrollState;
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                // Nothing to do in IDLE state.
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE)
                    return;

                for (int i=0; i < visibleItemCount; i++) {
                    View listItem = view.getChildAt(i);
                    if (listItem == null)
                        break;

                    LinearLayout title = (LinearLayout)listItem.findViewById(R.id.llTitle);

                    int topMargin = 0;

                    if (i == 0) {
                        int top = listItem.getTop();
                        int height = listItem.getHeight();

                        // if top is negative, the list item has scrolled up.
                        // if the title view falls within the container's visible portion,
                        //     set the top margin to be the (inverse) scrolled amount of the container.
                        // else
                        //     set the top margin to be the difference between the heights.
                        if (top < 0)
                            topMargin = title.getHeight() < (top + height) ? -top : (height - title.getHeight());
                    }
                    // set the margin.
                    ((ViewGroup.MarginLayoutParams) title.getLayoutParams()).topMargin = topMargin;
                    // request Android to layout again.
                    listItem.requestLayout();
                }
            }
        });
    }

    private void fetchPopularPhotos() {
        String finalURL = baseURL + clientID;
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(finalURL,null, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            photoAdapter.clear();
                            JSONArray photoDataArray = response.getJSONArray("data");
                            photoAdapter.addAll(Photo.populatePhotoInfoFromJSON(photoDataArray));
                            progressBar.setVisibility(View.INVISIBLE);
                            //photoAdapter.notifyDataSetInvalidated();
                        } catch (JSONException e) {
                            Log.i("Error....", e.toString());
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        progressBar.setVisibility(View.INVISIBLE);
                        NetworkErrorDialog errorDialog = new NetworkErrorDialog(PopularPhotoActivity.this);
                        errorDialog.createNetworkErrorDialog();
                    }
                }
        );
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_popular_photo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
