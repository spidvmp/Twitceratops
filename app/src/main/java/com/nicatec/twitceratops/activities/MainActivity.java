package com.nicatec.twitceratops.activities;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.nicatec.twitceratops.R;
import com.nicatec.twitceratops.adapters.TweeterMessageAdapter;
import com.nicatec.twitceratops.fragments.MapFragment;
import com.nicatec.twitceratops.fragments.SearchTextViewFragment;
import com.nicatec.twitceratops.fragments.TweetsFragment;
import com.nicatec.twitceratops.model.TweetDAO;
import com.nicatec.twitceratops.model.Tweets;
import com.nicatec.twitceratops.util.twitter.ConnectTwitterTask;
import com.nicatec.twitceratops.util.twitter.TwitterHelper;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import twitter4j.AccountSettings;
import twitter4j.AsyncTwitter;
import twitter4j.Category;
import twitter4j.DirectMessage;
import twitter4j.Friendship;
import twitter4j.IDs;
import twitter4j.OEmbed;
import twitter4j.PagableResponseList;
import twitter4j.Place;
import twitter4j.QueryResult;
import twitter4j.RateLimitStatus;
import twitter4j.Relationship;
import twitter4j.ResponseList;
import twitter4j.SavedSearch;
import twitter4j.Status;
import twitter4j.Trends;
import twitter4j.TwitterAPIConfiguration;
import twitter4j.TwitterException;
import twitter4j.TwitterListener;
import twitter4j.TwitterMethod;
import twitter4j.User;
import twitter4j.UserList;
import twitter4j.api.HelpResources;
import twitter4j.auth.AccessToken;
import twitter4j.auth.OAuth2Token;
import twitter4j.auth.RequestToken;

public class MainActivity extends AppCompatActivity implements ConnectTwitterTask.OnConnectTwitterListener, SearchTextViewFragment.SearchedTextListened, OnMapReadyCallback, LocationListener {

    @Bind(R.id.button)
    Button button;

    @Bind(R.id.fragment_search_text_view)
    FrameLayout fragmentSearchView;

    @Bind(R.id.activity_main_fragment_map)
    FrameLayout fragmentMap;

    private SearchTextViewFragment searchTextViewFragment;
    private MapFragment mapFragment;

    ConnectTwitterTask twitterTask;
    private static final int URL_LOADER = 0;

    TweetDAO tweetDAO;
    Tweets tweets;


    public static GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        //al arrancar comienzo con lo qu haya en la BD
        //TweetDAO tweetDAO = new TweetDAO(this);
        //Tweets tweets = tweetDAO.query();



        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //pongo mi posicion

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_map);
        mapFragment.getMapAsync(this);

        /*

         //con esto me conecto a twitter
        if (com.nicatec.twitceratops.util.NetworkHelper.isNetworkConnectionOK(new WeakReference<>(getApplication()))) {
            twitterTask = new ConnectTwitterTask(this);
            twitterTask.setListener(this);

            twitterTask.execute();
        } else {
            Toast.makeText(this, getString(R.string.error_network), Toast.LENGTH_LONG).show();

        }
*/

    //con esto muestra lo que hay en la BD
        FragmentManager fm = getSupportFragmentManager();
        if ( fm != null ){

            TweetsFragment tf = new TweetsFragment();
            fm.beginTransaction()
                    .add(fragmentMap.getId() , tf)
                    .commit();
        }





        /*
        RecyclerView rv = new RecyclerView(this);
        rv.setLayoutManager(new LinearLayoutManager(this));
        TweeterMessageAdapter adapter = new TweeterMessageAdapter(tweets, this);
        rv.setAdapter(adapter);
*/
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //launchTwitter();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if ( item.getItemId() == R.id.action_search) {
        //if ( item == actionSearch) {

            FragmentManager fm = getSupportFragmentManager();

            if ( fm != null ){

                searchTextViewFragment  = new SearchTextViewFragment();
                fm.beginTransaction()
                        .add(fragmentSearchView.getId(),  searchTextViewFragment)
                        .commit();

            }

        }

        return super.onContextItemSelected(item);
    }

    @Override
    public void OnNewLocationToSearch(String locationString) {
        Log.v("MainActivity","OnNewLocationToSearch recibe para buscar:" + locationString);
        //quito el fragment

        FragmentManager fm = getSupportFragmentManager();
        if ( fm != null ){
            //MapFragment mf = new MapFragment();
            fm.beginTransaction()
                    .remove(searchTextViewFragment)
                    .commit();
        }

        Geocoder gc = new Geocoder(this);

        if ( gc.isPresent() ) {

            List<Address> list = null;
            try {
                //obtener las coordenadas del sition
                list = gc.getFromLocationName(locationString, 1);

            } catch (Exception e) {
                e.printStackTrace();
            }

            if (list.size() == 1) {
                //tengo alguna coordenada
                if (list.get(0).hasLatitude() && list.get(0).hasLongitude()){
                    LatLng position = new LatLng(list.get(0).getLatitude(), list.get(0).getLongitude());
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(position,13));

                    TweetsFragment tf = new TweetsFragment();
                    fm.beginTransaction()
                            .add(fragmentMap.getId() , tf)
                            .commit();
                }
            }

            //actualizo la vista
            //mapFragment.refreshView();
            //LatLng sydney = new LatLng(40.42234, -3.6976);
            //map.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
            //map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,15));

/*
            FragmentManager fm = getSupportFragmentManager();
            if ( fm != null ){

                TweetsFragment tf = new TweetsFragment();
                fm.beginTransaction()
                        .add(fragmentMap.getId() , tf)
                        .commit();
            }
  */

/*


            tweets = tweetDAO.query();
            //adapter = new TweeterMessageAdapter(tweets, getActivity());
            //mapRecyclerView.setAdapter( adapter);
            //recyclerView.setAdapter(adapter);




            RecyclerView rv = new RecyclerView(this);
            TweeterMessageAdapter adapter = new TweeterMessageAdapter(tweets, this);
            rv.setAdapter(adapter);

*/
            //De momento muestro aqui los tweets que tengo
        } else {
            Log.v("Geocoder","No hay servicio ecxterno");
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        map = googleMap;
        map.setMyLocationEnabled(true);


        //LatLng sydney = new LatLng(40.42234, -3.6976);
        //map.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,15));
        //map.animateCamera(CameraUpdateFactory.zoomTo(15));
        /*
        Marker aqui = map.addMarker(new MarkerOptions()

                .position(sydney)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.app))
                .title("Aqui"));
        aqui.showInfoWindow();
        */
        Log.v("MAPREADY","MAPA CARGADO----------------------------");

    }

    @Override
    public void onLocationChanged(Location location) {

        Log.v("MainActivity","Nueva localizacion del mapa " + location);

    }

    private void launchTwitter() {
        AsyncTwitter twitter = new TwitterHelper(this).getAsyncTwitter();
        twitter.addListener(new TwitterListener() {
            @Override
            public void gotMentions(ResponseList<Status> statuses) {

            }

            @Override
            public void gotHomeTimeline(ResponseList<Status> statuses) {

            }

            @Override
            public void gotUserTimeline(ResponseList<Status> statuses) {
                for (Status s: statuses) {
                    Log.d("Twitter", "tweet: " + s.getText());
                }
            }

            @Override
            public void gotRetweetsOfMe(ResponseList<Status> statuses) {

            }

            @Override
            public void gotRetweets(ResponseList<Status> retweets) {

            }

            @Override
            public void gotShowStatus(Status status) {

            }

            @Override
            public void destroyedStatus(Status destroyedStatus) {

            }

            @Override
            public void updatedStatus(Status status) {

            }

            @Override
            public void retweetedStatus(Status retweetedStatus) {

            }

            @Override
            public void gotOEmbed(OEmbed oembed) {

            }

            @Override
            public void lookedup(ResponseList<Status> statuses) {

            }

            @Override
            public void searched(QueryResult queryResult) {

            }

            @Override
            public void gotDirectMessages(ResponseList<DirectMessage> messages) {

            }

            @Override
            public void gotSentDirectMessages(ResponseList<DirectMessage> messages) {

            }

            @Override
            public void gotDirectMessage(DirectMessage message) {

            }

            @Override
            public void destroyedDirectMessage(DirectMessage message) {

            }

            @Override
            public void sentDirectMessage(DirectMessage message) {

            }

            @Override
            public void gotFriendsIDs(IDs ids) {

            }

            @Override
            public void gotFollowersIDs(IDs ids) {

            }

            @Override
            public void lookedUpFriendships(ResponseList<Friendship> friendships) {

            }

            @Override
            public void gotIncomingFriendships(IDs ids) {

            }

            @Override
            public void gotOutgoingFriendships(IDs ids) {

            }

            @Override
            public void createdFriendship(User user) {

            }

            @Override
            public void destroyedFriendship(User user) {

            }

            @Override
            public void updatedFriendship(Relationship relationship) {

            }

            @Override
            public void gotShowFriendship(Relationship relationship) {

            }

            @Override
            public void gotFriendsList(PagableResponseList<User> users) {

            }

            @Override
            public void gotFollowersList(PagableResponseList<User> users) {

            }

            @Override
            public void gotAccountSettings(AccountSettings settings) {

            }

            @Override
            public void verifiedCredentials(User user) {
                Log.d("TWEET", "verifiedCredentials " + user);

            }

            @Override
            public void updatedAccountSettings(AccountSettings settings) {

            }

            @Override
            public void updatedProfile(User user) {

            }

            @Override
            public void updatedProfileBackgroundImage(User user) {

            }

            @Override
            public void updatedProfileColors(User user) {

            }

            @Override
            public void updatedProfileImage(User user) {

            }

            @Override
            public void gotBlocksList(ResponseList<User> blockingUsers) {

            }

            @Override
            public void gotBlockIDs(IDs blockingUsersIDs) {

            }

            @Override
            public void createdBlock(User user) {

            }

            @Override
            public void destroyedBlock(User user) {

            }

            @Override
            public void lookedupUsers(ResponseList<User> users) {

            }

            @Override
            public void gotUserDetail(User user) {

            }

            @Override
            public void searchedUser(ResponseList<User> userList) {

            }

            @Override
            public void gotContributees(ResponseList<User> users) {

            }

            @Override
            public void gotContributors(ResponseList<User> users) {

            }

            @Override
            public void removedProfileBanner() {

            }

            @Override
            public void updatedProfileBanner() {

            }

            @Override
            public void gotMutesList(ResponseList<User> blockingUsers) {

            }

            @Override
            public void gotMuteIDs(IDs blockingUsersIDs) {

            }

            @Override
            public void createdMute(User user) {

            }

            @Override
            public void destroyedMute(User user) {

            }

            @Override
            public void gotUserSuggestions(ResponseList<User> users) {

            }

            @Override
            public void gotSuggestedUserCategories(ResponseList<Category> category) {

            }

            @Override
            public void gotMemberSuggestions(ResponseList<User> users) {

            }

            @Override
            public void gotFavorites(ResponseList<Status> statuses) {

            }

            @Override
            public void createdFavorite(Status status) {

            }

            @Override
            public void destroyedFavorite(Status status) {

            }

            @Override
            public void gotUserLists(ResponseList<UserList> userLists) {
                Log.d("TWEET", "gotUserLists");

            }

            @Override
            public void gotUserListStatuses(ResponseList<Status> statuses) {

            }

            @Override
            public void destroyedUserListMember(UserList userList) {

            }

            @Override
            public void gotUserListMemberships(PagableResponseList<UserList> userLists) {

            }

            @Override
            public void gotUserListSubscribers(PagableResponseList<User> users) {

            }

            @Override
            public void subscribedUserList(UserList userList) {

            }

            @Override
            public void checkedUserListSubscription(User user) {

            }

            @Override
            public void unsubscribedUserList(UserList userList) {

            }

            @Override
            public void createdUserListMembers(UserList userList) {

            }

            @Override
            public void checkedUserListMembership(User users) {

            }

            @Override
            public void createdUserListMember(UserList userList) {

            }

            @Override
            public void destroyedUserList(UserList userList) {

            }

            @Override
            public void updatedUserList(UserList userList) {

            }

            @Override
            public void createdUserList(UserList userList) {

            }

            @Override
            public void gotShowUserList(UserList userList) {

            }

            @Override
            public void gotUserListSubscriptions(PagableResponseList<UserList> userLists) {

            }

            @Override
            public void gotUserListMembers(PagableResponseList<User> users) {

            }

            @Override
            public void gotSavedSearches(ResponseList<SavedSearch> savedSearches) {

            }

            @Override
            public void gotSavedSearch(SavedSearch savedSearch) {

            }

            @Override
            public void createdSavedSearch(SavedSearch savedSearch) {

            }

            @Override
            public void destroyedSavedSearch(SavedSearch savedSearch) {

            }

            @Override
            public void gotGeoDetails(Place place) {

            }

            @Override
            public void gotReverseGeoCode(ResponseList<Place> places) {

            }

            @Override
            public void searchedPlaces(ResponseList<Place> places) {

            }

            @Override
            public void gotSimilarPlaces(ResponseList<Place> places) {

            }

            @Override
            public void gotPlaceTrends(Trends trends) {

            }

            @Override
            public void gotAvailableTrends(ResponseList<twitter4j.Location> locations) {

            }

            @Override
            public void gotClosestTrends(ResponseList<twitter4j.Location> locations) {

            }

            @Override
            public void reportedSpam(User reportedSpammer) {

            }

            @Override
            public void gotOAuthRequestToken(RequestToken token) {

            }

            @Override
            public void gotOAuthAccessToken(AccessToken token) {

            }

            @Override
            public void gotOAuth2Token(OAuth2Token token) {

            }

            @Override
            public void gotAPIConfiguration(TwitterAPIConfiguration conf) {

            }

            @Override
            public void gotLanguages(ResponseList<HelpResources.Language> languages) {

            }

            @Override
            public void gotPrivacyPolicy(String privacyPolicy) {

            }

            @Override
            public void gotTermsOfService(String tof) {

            }

            @Override
            public void gotRateLimitStatus(Map<String, RateLimitStatus> rateLimitStatus) {

            }

            @Override
            public void onException(TwitterException te, TwitterMethod method) {

            }
        });
        twitter.getUserTimeline();
    }
    @Override
    public void twitterConnectionFinished() {
        Toast.makeText(this, getString(R.string.twiiter_auth_ok), Toast.LENGTH_SHORT).show();


    }


}
