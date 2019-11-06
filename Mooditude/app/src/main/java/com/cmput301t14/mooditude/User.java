package com.cmput301t14.mooditude;

import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User{
    private FirebaseUser user;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    private String userName;

    public User(){
        db=FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        fetchUserName();
    }

    public void pushMoodEvent(MoodEvent moodEvent){
        Location location  =moodEvent.getLocation();
        LocalDateTime localDateTime=moodEvent.getDatetime();
        Integer author= moodEvent.getAuthor();

        Mood mood = moodEvent.getMood();
        SocialSituation socialSituation= moodEvent.getSocialSituation();
        String textComment= moodEvent.getTextComment();


        CollectionReference moodHistory = db.collection("Users")
                .document(user.getEmail()).collection("MoodHistory");

        DocumentReference moodEntry=moodHistory.document(moodEvent.getDatetime().toString());

        HashMap<String,Object> moodHash = new HashMap<>();
        moodHash.put("Location",location.getGeopoint());
        moodHash.put("Mood",mood.getMood());
        moodHash.put("Comment",textComment);
        moodHash.put("DateTime",localDateTime.toString());
        moodHash.put("SocialSituation",socialSituation.getSocialSituation());

        moodEntry.set(moodHash);
    }

    public void deleteMoodEvent(MoodEvent selectedMoodEvent){
        CollectionReference moodHistory = db.collection("Users")
                .document(user.getEmail()).collection("MoodHistory");

        // delete the moodEvent from Firebase if it is selected,
        // Note the SnapshotListener will handle the local update as well
        moodHistory.document(selectedMoodEvent.getDatetime().toString())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "Data deletion successful");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Data deletion failed" + e.toString());
                    }
                });
    }

    public void listenSelfMoodEvents(final ArrayList<MoodEvent> moodEventDataList, final ArrayAdapter<MoodEvent> moodEventAdapter){
        CollectionReference collectionReference = db.collection("Users")
                .document(user.getEmail()).collection("MoodHistory");

        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                moodEventDataList.clear();
                for (QueryDocumentSnapshot doc: queryDocumentSnapshots){
                    Integer author=1;
                    String textComment=doc.getString("Comment");
                    Mood mood= new Mood(doc.getString("Mood"));
                    SocialSituation socialSituation= new SocialSituation(doc.getString("SocialSituation"));
                    Location location= new Location(doc.getGeoPoint("Location"));
                    LocalDateTime datetime = LocalDateTime.parse(doc.getString("DateTime"));
                    MoodEvent moodEvent=new MoodEvent(author, mood, location,socialSituation,textComment,datetime);
                    moodEventDataList.add(moodEvent);
                }
                moodEventAdapter.notifyDataSetChanged();
            }
        });
    }

    private void fetchUserName(){
        DocumentReference docRef = db.collection("Users").document(user.getEmail());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        userName = document.getData().get("user_name").toString();
                    } else {
                        Log.d("TAG", "No such document");
                    }
                } else {
                    Log.d("TAG", "get failed with ", task.getException());
                }
            }
        });
    }

    public String getUserName(){
        return this.userName;
    }


}
