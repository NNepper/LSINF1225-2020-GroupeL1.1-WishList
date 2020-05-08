package be.uclouvain.lsinf1225.groupeL11.wishlist.Interface.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import be.uclouvain.lsinf1225.groupeL11.wishlist.Backend.Interest;
import be.uclouvain.lsinf1225.groupeL11.wishlist.Backend.User;
import be.uclouvain.lsinf1225.groupeL11.wishlist.DAO.InterestDAO;
import be.uclouvain.lsinf1225.groupeL11.wishlist.Interface.HomeActivity;
import be.uclouvain.lsinf1225.groupeL11.wishlist.R;

public class InterestsListAdapter extends RecyclerView.Adapter<InterestsListAdapter.InterestsViewHolder> {
    private ArrayList<Interest> interestsList;
    private InterestsListAdapter.onItemClickListener interestsClickListener;
    private Context context;
    private User mainUser;
    private Boolean isMainUser;
    private int userID;

    public interface onItemClickListener{
        void onItemClick(int position);
        void onCheckClick(int position);
    }

    public void setOnItemClickLister(InterestsListAdapter.onItemClickListener listener){
        interestsClickListener = listener;
    }

    public static class InterestsViewHolder extends RecyclerView.ViewHolder{
        public CheckBox checkBox;
        public TextView interestName;

        public InterestsViewHolder(@NonNull View itemView, final InterestsListAdapter.onItemClickListener listener) {
            super(itemView);
            interestName = itemView.findViewById(R.id.interest_name);
            checkBox = itemView.findViewById(R.id.interest_item_checkbox);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });

            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) { // TODO changer pour pouvoir ajouter l'user à la liste du main
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onCheckClick(position);
                        }
                    }
                }
            });
        }
    }

    public InterestsListAdapter(ArrayList<Interest> interests, Context context, User mainUser, Boolean isMainUser, int userID){
        this.mainUser = mainUser;
        this.context = context;
        this.interestsList = interests;
        this.isMainUser = isMainUser;
        this.userID = userID;
    }

    @NonNull
    @Override
    public InterestsListAdapter.InterestsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_interest_item, parent, false);
        InterestsListAdapter.InterestsViewHolder flvh = new InterestsListAdapter.InterestsViewHolder(view, interestsClickListener);
        return flvh;
    }

    @Override
    public void onBindViewHolder(@NonNull InterestsListAdapter.InterestsViewHolder holder, int position) {
        Interest currentInterest = interestsList.get(position);
        InterestDAO interestDAO = new InterestDAO(this.context);
        if (isActiveInterest(currentInterest, interestDAO)) {
            holder.checkBox.setChecked(true);
        }
        holder.interestName.setText(currentInterest.getInterestName());
        if(! isMainUser){
            holder.checkBox.setEnabled(false);
        }
    }

    @Override
    public int getItemCount() {
        return interestsList.size();
    }

    private boolean isActiveInterest(Interest interest, InterestDAO interestDAO) {
        for (Interest current : interestDAO.readInterests(userID)) {
            if (interest.getInterestName().equals(current.getInterestName())) {
                return true;
            }
        }
        return false;
    }
}
