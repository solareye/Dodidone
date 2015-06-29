package mobile.solareye.dodidone.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import mobile.solareye.dodidone.data.EventModel;
import mobile.solareye.dodidone.data.EventsDataProvider;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.recyclerview.internal.CardArrayRecyclerViewAdapter;
import it.gmariotti.cardslib.library.view.CardView;
import it.gmariotti.cardslib.library.view.base.CardViewWrapper;
import mobile.solareye.dodidone.R;

/**
 * Created by Aleksander on 2/25/2015.
 */
public class CardsRecyclerAdapter extends CardArrayRecyclerViewAdapter {
    public CardsRecyclerAdapter(Context context, List<Card> cards, EventsDataProvider eventsDataProvider) {
        super(context, cards);

        this.mContext = context;
        this.eventsDataProvider = eventsDataProvider;
        this.mDataset = eventsDataProvider.getItems();

        setHasStableIds(true);
    }

    private List<EventModel> mDataset;
    private EventsDataProvider eventsDataProvider;
    private Context mContext;

    protected @LayoutRes int[] mRowLayoutIds;
    //protected @LayoutRes int mRowLayoutId = R.layout.main_card_view;
    protected @LayoutRes int mRowLayoutId = R.layout.list_card_layout;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    /*public static class CardViewHolder extends CardsRecyclerAdapter.CardViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        public CardView cardView;
        //public LinearLayout llExpandArea;
        public CardViewHolder(View v) {
            super(v);
            //mTextView = (TextView) v.findViewById(R.id.info_text);
            //llExpandArea = (LinearLayout) itemView.findViewById(R.id.llExpandArea);

            cardView = (CardView) v.findViewById(R.id.card_view);
        }
    }*/

    public static class ViewHolder extends CardViewHolder {
        public TextView mTextView;
        public CardView cardView;
        public final CardViewWrapper mCardView;
        public boolean recycled = false;
        //public LinearLayout llExpandArea;
        public ViewHolder(View v) {
            super(v);
            //mTextView = (TextView) v.findViewById(R.id.info_text);
            //llExpandArea = (LinearLayout) itemView.findViewById(R.id.llExpandArea);

            mCardView = (CardView) v.findViewById(R.id.list_cardId);
        }
    }

    /*public static class CardViewHolder extends RecyclerView.ViewHolder {

        public final CardViewWrapper mCardView;
        public boolean recycled = false;

        public CardViewHolder(View view) {
            super(view);
            mCardView = (CardViewWrapper) view.findViewById(R.id.card_view);
        }
    }*/

    // Provide a suitable constructor (depends on the kind of dataset)


    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        /*// create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_card_view, parent, false);

        CardViewHolder vh = new CardViewHolder(v);

        return vh;*/

        if (mRowLayoutIds == null || mRowLayoutIds.length == 0) {
            final View view = LayoutInflater.from(parent.getContext()).inflate(mRowLayoutId, parent, false);
            return new ViewHolder(view);
        } else {
            final View view = LayoutInflater.from(parent.getContext()).inflate(mRowLayoutIds[viewType], parent, false);
            return new ViewHolder(view);
        }
    }

    @Override
    public long getItemId(int position) {
        return mDataset.get(position).hashCode();
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        //holder.mTextView.setText(mDataset.get(position).getName());


        CardViewWrapper mCardView = holder.mCardView;
        Card mCard = getItem(position);

        //Setup card
        if (mCardView != null) {
            //It is important to set recycle value for inner layout elements
            mCardView.setForceReplaceInnerLayout(Card.equalsInnerLayout(mCardView.getCard(),mCard));

            //It is important to set recycle value for performance issue
            mCardView.setRecycle(holder.recycled);

            //Save original swipeable to prevent cardSwipeListener (listView requires another cardSwipeListener)
            boolean origianlSwipeable = mCard.isSwipeable();
            mCard.setSwipeable(true);

            mCardView.setCard(mCard);

            //Set originalValue
            mCard.setSwipeable(origianlSwipeable);

            //If card has an expandable button override animation
            if ((mCard.getCardHeader() != null && mCard.getCardHeader().isButtonExpandVisible()) || mCard.getViewToClickToExpand()!=null ){
                setupExpandCollapseListAnimation(mCardView);
            }

            //Setup swipeable animation
            //setupSwipeableAnimation(mCard, mCardView);
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void setRowLayoutIds(@LayoutRes int[] rowLayoutIds) {
        mRowLayoutIds = rowLayoutIds;
        if (rowLayoutIds != null)
            typeCardCount = rowLayoutIds.length;
        else
            typeCardCount = 1;
    }
}
