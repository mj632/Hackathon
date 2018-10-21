package com.example.nk.safehomesearch;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.facebook.CallbackManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.stitch.android.core.Stitch;
import com.mongodb.stitch.android.core.StitchAppClient;
import com.mongodb.stitch.android.core.auth.StitchAuth;
import com.mongodb.stitch.android.core.auth.StitchAuthListener;
import com.mongodb.stitch.android.core.auth.StitchUser;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoClient;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoCollection;
import com.mongodb.stitch.core.services.mongodb.remote.RemoteInsertOneResult;
import com.mongodb.stitch.core.services.mongodb.remote.RemoteUpdateResult;

import org.bson.Document;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.mongodb.client.model.Filters.eq;

public class MongoDBHelper {
    public StitchAppClient client;
    static RemoteMongoCollection<Document> coll;
    static RemoteMongoClient mongoClient;
    private static final String TAG = "MongodbHelper";
    ReviewActivity reviewActivity;
    SafeHomeDetailsActivity safeHomeDetailsActivity;

    public MongoDBHelper(ReviewActivity reviewActivity){
        this.reviewActivity = reviewActivity;
        client = Stitch.getAppClient("boilermakestitch-ldtyd");
        client.getAuth().addAuthListener(new MyAuthListener(reviewActivity));
        mongoClient = client.getServiceClient(RemoteMongoClient.factory, "mongodb-atlas");
        coll = mongoClient.getDatabase("test2").getCollection("mapstest");
    }

    public MongoDBHelper(SafeHomeDetailsActivity safeHomeDetailsActivity){
        this.safeHomeDetailsActivity = safeHomeDetailsActivity;
        client = Stitch.getAppClient("boilermakestitch-ldtyd");
        client.getAuth().addAuthListener(new MyAuthListener(reviewActivity));
        mongoClient = client.getServiceClient(RemoteMongoClient.factory, "mongodb-atlas");
        coll = mongoClient.getDatabase("test2").getCollection("mapstest");
    }

    public MongoDBHelper() {

        // Initialize stitch related components
        client =  Stitch.initializeDefaultAppClient("boilermakestitch-ldtyd");
        client.getAuth().addAuthListener(new MyAuthListener(reviewActivity));
        mongoClient = client.getServiceClient(RemoteMongoClient.factory, "mongodb-atlas");
        coll = mongoClient.getDatabase("test1").getCollection("mapstest");
    }


    private class MyAuthListener implements StitchAuthListener {
        private WeakReference<ReviewActivity> _main;
        private StitchUser _user;

        public MyAuthListener(final ReviewActivity activity) {
            _main = new WeakReference<>(activity);
        }

        @Override
        public void onAuthEvent(final StitchAuth auth) {
            if (auth.isLoggedIn() && _user == null) {
                Log.d(TAG, "Logged into Stitch");
                _user = auth.getUser();
                return;
            }

        }
    }

    void addReviewDetails(double longitude, double latitude, String review, String userID){
        final Document doc = new Document();
        String userId = userID;
        doc.put("userID", userID);
        doc.put("latitude", latitude);
        doc.put("longitude", longitude);
        doc.put("reviews",review);

        final Task<RemoteInsertOneResult> res = coll.insertOne(doc);
        res.addOnCompleteListener(new OnCompleteListener<RemoteInsertOneResult>() {
            @Override
            public void onComplete(@NonNull Task<RemoteInsertOneResult> task) {
                if(task.isSuccessful()){
                    System.out.println("recorded inserted");
                } else{
                    Log.e(TAG, "Error adding item", task.getException());
                }
            }
        });
    }

    void insertMoreReviews(double longitude, double latitude, ArrayList<String> review, String userID){
        final Document doc = new Document();

        doc.put("userID", userID);
        doc.put("latitude", latitude);
        doc.put("longitude", longitude);
        doc.put("reviews",review);
        Task<RemoteUpdateResult> u  = coll.updateOne(eq("userID", userID), Updates.push("reviews", review));
        u.addOnCompleteListener(new OnCompleteListener<RemoteUpdateResult>() {
            @Override
            public void onComplete(@NonNull Task<RemoteUpdateResult> task) {
                if(task.isSuccessful()){
                    reviewActivity.goBackTOParent();
                } else{
                    Log.e(TAG, "Error adding item", task.getException());
                    reviewActivity.goBackTOParent();
                }
            }
        });
    }

    ArrayList<String> viewReviews(double longitude, double latitude){
        ArrayList<String> getReviews = new ArrayList<String>();
        Task<Document> doc = coll.find(eq("latitude",latitude)).first();
        Log.d(TAG,doc.toString());
        return null;
    }


}
