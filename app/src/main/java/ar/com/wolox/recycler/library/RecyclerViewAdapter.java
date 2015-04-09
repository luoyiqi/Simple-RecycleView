package ar.com.wolox.recycler.library;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;

import ar.com.wolox.recycler.library.entities.RecyclerViewItemInterface;

public abstract class RecyclerViewAdapter<E extends RecyclerViewItemInterface> extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private ArrayList<E> mItems = new ArrayList<E>();
    private ArrayList<Integer> mLoaders = new ArrayList<Integer>();

    //The following code is used to get instances of a generic class by reflection
    //private Class<E> mItemsType;
    private E mItemsInstance;
    public RecyclerViewAdapter(Class<E> classType, E item)
    {
        //this.mItemsType = classType;
        this.mItemsInstance = item;
    }
    //public Class<E> getItemsType(){return mItemsType;}

    /*********
    The custom adapter that extends this adapter MUST implement this methods


    @Override
    public  RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {

    }

    *********/

    // Return the size of your mItemsNews (invoked by the layout manager)
    @Override
    public int getItemCount() { return mItems.size(); }

    public ArrayList<E> getItems() {
        return mItems;
    }

    public void setItems(ArrayList<E> mItems) {
        this.mItems = mItems;
    }

    //** Start of DATASET MANIPULATORS **
    public void addItem(E item) {
        addItemToPos(getItemCount(), item);
    }

    public void addItemToPos(int position, E item) {
        mItems.add(position, item);
        notifyItemInserted(position);
    }

    public void addAllItems(Collection<E> itemsArray) {
        for(E item : itemsArray) {
            addItem(item);
        }
    }

    public void removeItemByPos(int position) {
        mItems.remove(position);
        notifyItemRemoved(position);
    }

    public int getItemPosition(E itemToLocate) {
        return mItems.indexOf(itemToLocate);
    }

    public void removeItem(E itemToRemove) {
        int position = getItemPosition(itemToRemove);
        if (position >= 0) removeItemByPos(position);
    }

    public void moveItem(int fromPos, int toPos) {
        E temp = mItems.remove(fromPos);
        mItems.add(toPos, temp);
    }

    //** End of DATASET MANIPULATORS **

    //** Start of LOADERS **

    public void addLoadingRow(int position) {
        this.addItemToPos(position, (E) mItemsInstance.create());
        mLoaders.add(position);
    }

    public void addLoadingRow() {
        this.addItem((E) mItemsInstance.create());
        mLoaders.add(getItemCount() - 1);
    }

    public void removeLastLoadingRow() {
        removeLoadingRowByPos(mLoaders.size() - 1);
    }

    public void removeLoadingRow(int position) {
        int loaderPosition = mLoaders.get(position);
        if (loaderPosition >= 0)
        {
            removeItemByPos(loaderPosition);
            removeLoadingRowByPos(position);
        }
    }

    protected void removeLoadingRowByPos(int position) {
        int posInItems = mLoaders.get(position);
        if (posInItems >= 0) {
            mItems.remove(posInItems);
            mLoaders.remove(position);
        }
    }

    public boolean isLoader(int position) {
        for(int aLoaderPosition : mLoaders) {
            if (aLoaderPosition == position) return true;
        }
    return false;
    }

    //** End of LOADERS **

}
