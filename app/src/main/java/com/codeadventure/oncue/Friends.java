package com.codeadventure.oncue;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.codeadventure.oncue.database.offline.SharedData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Friends extends Fragment {

    RecyclerView recyclerView;
    TextView emptyText;
    EditText editTextSearch;
    View view;
    FriendsAdapter friendsAdapter;
    List<FriendsAdapter.FriendItem> friendList;
    List<FriendsAdapter.FriendItem> search_friendList;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_friends, container, false);
        System.out.println("Here");

        recyclerView = (RecyclerView) view.findViewById(R.id.friends_list);
        emptyText = (TextView) view.findViewById(R.id.nofriend);
        friendList = new ArrayList<>();
        FriendsAdapter.FriendItem remind;
        editTextSearch = (EditText) view.findViewById(R.id.search);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        //ImageButton sync = (ImageButton) view.findViewById(R.id.sync);

        try {
            SharedData sd = new SharedData();
            int i;

            for (i = 0; i < sd.getFriendEntity().size(); i++) {
                remind = new FriendsAdapter.FriendItem(sd.getFriendEntity().get(i).getFACEBOOK_ID(), sd.getFriendEntity().get(i).getFNAME() + " " + sd.getFriendEntity().get(i).getLNAME()
                        , sd.getFriendEntity().get(i).getDP());
                friendList.add(remind);
            }
            Collections.sort(friendList, FriendsAdapter.FriendNameComparator);


            if (friendList.size() == 0) {
                emptyText.setVisibility(View.VISIBLE);
            }

            friendsAdapter = new FriendsAdapter(friendList);
            recyclerView.setAdapter(friendsAdapter);
            friendsAdapter.notifyDataSetChanged();

            addTextListener();

            return view;
        }
        catch (Exception e)
        {
            System.out.println("Exception Record : "+e);
        }
        return view;
    }

   /* public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search,menu);
        MenuItem item = menu.findItem(R.id.menusearch);
        SearchView searchview = (SearchView)item.getActionView();
        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                search_friendList = new ArrayList<>();
                query = query.toLowerCase();
                int i;
                for(i=0;i<friendList.size();i++){
                    final String text = friendList.get(i).name.toLowerCase();
                    if (text.contains(query)) {
                        search_friendList.add(friendList.get(i));
                    }
                }
                friendsAdapter = new FriendsAdapter(search_friendList);
                recyclerView.setAdapter(friendsAdapter);
                friendsAdapter.notifyDataSetChanged();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                search_friendList = new ArrayList<>();
                newText = newText.toLowerCase();
                int i;
                for(i=0;i<friendList.size();i++){
                    final String text = friendList.get(i).name.toLowerCase();
                    if (text.contains(newText)) {
                        search_friendList.add(friendList.get(i));
                    }
                }
                friendsAdapter = new FriendsAdapter(search_friendList);
                recyclerView.setAdapter(friendsAdapter);
                friendsAdapter.notifyDataSetChanged();
                return false;
            }
        });
        super.onCreateOptionsMenu(menu,inflater);

    }*/

    public void addTextListener(){

        editTextSearch.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence query, int start, int before, int count) {

                query = query.toString().toLowerCase();

                search_friendList = new ArrayList<>();

                for (int i = 0; i < friendList.size(); i++) {

                    final String text = friendList.get(i).name.toLowerCase();
                    if (text.contains(query)) {

                        search_friendList.add(friendList.get(i));
                    }
                }

                friendsAdapter = new FriendsAdapter(search_friendList);
                recyclerView.setAdapter(friendsAdapter);
                friendsAdapter.notifyDataSetChanged();
            }
        });
    }

}
