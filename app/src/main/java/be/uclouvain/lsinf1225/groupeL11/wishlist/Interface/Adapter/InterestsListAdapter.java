package be.uclouvain.lsinf1225.groupeL11.wishlist.Interface.Adapter;

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
import be.uclouvain.lsinf1225.groupeL11.wishlist.R;

public class InterestsListAdapter extends RecyclerView.Adapter<InterestsListAdapter.InterestsViewHolder> {
    private ArrayList<Interest> interestsList;
    private InterestsListAdapter.onItemClickListener interestsClickListener;

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

    public InterestsListAdapter(ArrayList<Interest> interests){
        this.interestsList = interests;
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

        holder.interestName.setText(currentInterest.getInterestName());
    }

    @Override
    public int getItemCount() {
        return interestsList.size();
    }
}
