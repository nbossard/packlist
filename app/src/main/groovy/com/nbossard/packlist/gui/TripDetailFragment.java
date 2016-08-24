/*
 * PackList is an open-source packing-list for Android
 *
 * Copyright (c) 2016 Nicolas Bossard and other contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.nbossard.packlist.gui;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.ShareCompat;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nbossard.packlist.R;
import com.nbossard.packlist.databinding.FragmentTripDetailBinding;
import com.nbossard.packlist.model.Item;
import com.nbossard.packlist.model.SortModes;
import com.nbossard.packlist.model.Trip;
import com.nbossard.packlist.model.TripFormatter;
import com.nbossard.packlist.process.ImportExport;
import com.nbossard.packlist.process.saving.ISavingModule;

import java.util.List;

import hugo.weaving.DebugLog;
/*
@startuml
    class com.nbossard.packlist.gui.TripDetailFragment {
    }
    com.nbossard.packlist.gui.ItemAdapter <-- com.nbossard.packlist.gui.TripDetailFragment
    com.nbossard.packlist.gui.TripDetailFragment --> com.nbossard.packlist.gui.ITripDetailFragmentActivity

@enduml
 */

/**
 * Open a Trip for viewing / editing.
 * @author Created by nbossard on 09/01/16.
 */
public class TripDetailFragment extends Fragment {

    // ********************** CONSTANTS *********************************************************************

    /**
     * Log tag.
     */
    private static final String TAG = TripDetailFragment.class.getName();

    /** Bundle mandatory parameter when instantiating this fragment. */
    private static final String BUNDLE_PAR_TRIP_ID = "bundleParTripId";

    // *********************** FIELDS ***********************************************************************

    /** The root view, will be used to findViewById. */
    private View mRootView;

    /** Trip object to be displayed and added item. */
    private Trip mRetrievedTrip;

    /** Supporting activity, to save trip.*/
    private ITripDetailFragmentActivity mIHostingActivity;

    /**
     * The object to support Contextual Action Bar (CAB).
     */
    private ActionMode mActionMode;

    /** List of {@link Item} view. */
    private ListView mItemListView;

    /** Adapter for list view. */
    private ItemAdapter mListItemAdapter;

    /** Current hot item, the one to scroll to if list is refreshed. */
    private Item mHotItem;

    /** Edit text. */
    private AppCompatAutoCompleteTextView mNewItemEditText;

    /** Add item button. */
    private Button mAddItemButton;

    /** Add detailed item button. */
    @SuppressWarnings("FieldCanBeLocal")
    private Button mAddDetailedItemButton;

    /**
     * Add magic item button.
     */
    @SuppressWarnings("FieldCanBeLocal")
    private AppCompatImageButton mAddMagicItemButton;

    /**
     * List of items that may probably be added to this list, based on previous trips.
     */
    private List<String> mProbableItemsList;

    /**
     * Index of last suggestion in {@link #mProbableItemsList}.
     */
    private int mSuggestionIndex;

    // *********************** LISTENERS ********************************************************************
    /**
     * Listener for click on one item of the list.
     * Opens a new fragment displaying detail on item.
     */
    private final AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        @DebugLog
        public void onItemClick(final AdapterView<?> parent,
                                final View view,
                                final int parPosition,
                                final long id) {
            Item selectedItem = (Item) mItemListView.getItemAtPosition(parPosition);
            selectedItem.setPacked(!selectedItem.isPacked());
            mRetrievedTrip.packingChange();
            mIHostingActivity.saveTrip(mRetrievedTrip);
            mListItemAdapter.notifyDataSetChanged();
        }
    };
    /**
     * Listener for long click on one item of the list.
     * Opens the contextual action bar.
     */
    @NonNull
    private final AdapterView.OnItemLongClickListener mLongClickListener =
            new AdapterView.OnItemLongClickListener() {
        @DebugLog
        @Override
        public boolean onItemLongClick(final AdapterView<?> arg0, final View arg1,
                                       final int pos, final long id) {

            // keep item selected
            mItemListView.setItemChecked(pos, true);

            // starting action mode
            mActionMode = getActivity().startActionMode(new ActionMode.Callback() {
                @Override
                public boolean onCreateActionMode(final ActionMode mode, final Menu menu) {
                    mode.setTitle("Selected");

                    MenuInflater inflater = mode.getMenuInflater();
                    inflater.inflate(R.menu.menu_trip_detail_cab, menu);
                    return true;
                }

                @Override
                public boolean onPrepareActionMode(final ActionMode mode, final Menu menu) {
                    return false;
                }

                @Override
                public boolean onActionItemClicked(final ActionMode mode, final MenuItem item) {
                    int position;
                    boolean res;
                    switch (item.getItemId()) {
                        case R.id.action_trip_cab__delete:
                            position = (int) mActionMode.getTag();
                            deleteItemClicked(position);
                            res = true;
                            break;
                        case R.id.action_trip_cab__edit:
                            position = (int) mActionMode.getTag();
                            editItemClicked(position);
                            res = true;
                            break;
                        default:
                            res =  false;
                    }
                    return res;
                }

                @Override
                public void onDestroyActionMode(final ActionMode mode) {
                }
            });

            // mActionMode can be null if canceled
            if (mActionMode != null) {
                mActionMode.setTag(pos);
                arg1.setSelected(true);
            }

            return true;
        }

    };


    // *********************** METHODS **********************************************************************

    /**
     * Create a new instance of MyFragment that will be initialized
     * with the given arguments.
     * @param parTrip trip to be displayed
     * @return a TripDetailFragment called with accurate arguments
     */
    public static TripDetailFragment newInstance(final Trip parTrip) {
        TripDetailFragment f = new TripDetailFragment();
        Bundle b = new Bundle();
        b.putSerializable(BUNDLE_PAR_TRIP_ID, parTrip);
        f.setArguments(b);
        return f;
    }


    @Override
    public final void onAttach(final Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach() : Entering");

        // Management of FAB, forcing hiding of FAB, see also onDetach
        mIHostingActivity = (ITripDetailFragmentActivity) getActivity();
        mIHostingActivity.showFABIfAccurate(false);
    }

    /**
     * During creation, if arguments have been supplied to the fragment
     * then parse those out.
     */
    @Override
    public final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate() : Entering");

        Bundle args = getArguments();
        if (args != null) {
            mRetrievedTrip = (Trip) args.getSerializable(BUNDLE_PAR_TRIP_ID);
        }
    }

    @Override
    public final void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() : Entering");

        // Should normally be enough to do following in onattach...
        // but when activity is resumed and has been killed by system, onattach is not called.
        mIHostingActivity.showFABIfAccurate(false);
    }

    /**
    * Create the view for this fragment, using the arguments given to it.
    */
    @Override
    public final View onCreateView(final LayoutInflater inflater,
                                       final ViewGroup container,
                                       final Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView() : Entering");

        mRootView = inflater.inflate(R.layout.fragment_trip_detail, container, false);

        displayTrip(mRetrievedTrip);
        mIHostingActivity.updateTitleBar(
                String.format(getString(R.string.trip_detail__main__titlebar), mRetrievedTrip.getName()));

        return mRootView;
    }


    @DebugLog
    @Override
    public final void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated() : Entering");

        // custom menu for this fragment
        setHasOptionsMenu(true);

        // TODO old style, improve this
        mNewItemEditText = (AppCompatAutoCompleteTextView) mRootView.findViewById(R.id.trip_detail__new_item__edit);

        // pre-filling list of already existing categories that may match
        String[] alreadyExistCat = mIHostingActivity.getListOfItemNames();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, alreadyExistCat);
        mNewItemEditText.setAdapter(adapter);

        mAddItemButton = (Button) mRootView.findViewById(R.id.trip_detail__new_item__button);
        mAddItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                onClickAddItem();
            }
        });
        mAddItemButton.setEnabled(false);
        disableButtonIfEmptyText(mAddItemButton);

        mAddDetailedItemButton = (Button) mRootView.findViewById(R.id.trip_detail__new_item_detail__button);
        mAddDetailedItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                onClickAddDetailedItem();
            }
        });
        mAddDetailedItemButton.setEnabled(false);
        disableButtonIfEmptyText(mAddDetailedItemButton);

        mAddMagicItemButton = (AppCompatImageButton) mRootView.findViewById(R.id.trip_detail__new_item_magic__button);
        mAddMagicItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                onClickAddDMagicItem();
            }
        });

        // auto click on button if keyboard "enter" pressed
        mNewItemEditText.setOnEditorActionListener(new AppCompatEditText.OnEditorActionListener() {
            @DebugLog
            @Override
            public boolean onEditorAction(final TextView v, final int actionId, final KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    mAddItemButton.performClick();
                    handled = true;
                }
                return handled;
            }
        });

        final Button editButton = (Button) mRootView.findViewById(R.id.trip_detail__edit_button);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                onClickEditTrip();
            }
        });

        populateList();

    }

    @DebugLog
    @Override
    public final void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach() : Entering");

        mIHostingActivity.showFABIfAccurate(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() : Entering");
    }

    @Override
    public final void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
        inflater.inflate(R.menu.menu_trip_detail, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public final boolean onOptionsItemSelected(final MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.action_trip__share:
                ImportExport port = new ImportExport();
                Intent shareIntent = ShareCompat.IntentBuilder.from(getActivity())
                        .setType("text/plain")
                        .setText(port.toSharableString(getActivity(), mRetrievedTrip))
                        .getIntent();
                // Avoid ActivityNotFoundException
                if (shareIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(shareIntent);
                }
                break;
            case R.id.action_trip__import_txt:
                mIHostingActivity.openMassImportFragment(mRetrievedTrip);
                break;
            case R.id.action_trip__unpack_all:
                mRetrievedTrip.unpackAll();
                mIHostingActivity.saveTrip(mRetrievedTrip);
                // displayTrip(); automatically called back by saveTrip
                mListItemAdapter.notifyDataSetChanged();
                break;
            case R.id.action_trip__sort:
                SortModes curSortMode = mRetrievedTrip.getSortMode();
                SortModes newSortMode = curSortMode.next();
                mListItemAdapter.setSortMode(newSortMode);
                mRetrievedTrip.setSortMode(newSortMode);
                mIHostingActivity.saveTrip(mRetrievedTrip);
                mListItemAdapter.notifyDataSetChanged();
                Toast.makeText(TripDetailFragment.this.getActivity(),
                        String.format(getString(R.string.sorting_mode), getReadableName(newSortMode)),
                        Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /** Display provided trip.
     * Save it in {@link #mRetrievedTrip} as the trip currently being displayed.
     *
     * @param parTrip trip to be displayed
     */
    @DebugLog
    public final void displayTrip(final Trip parTrip) {

        mRetrievedTrip = parTrip;
        displayTrip();
    }

    /**
     * @return the {@link Trip} being currently displayed.
     */
    public final
    @Nullable
    Trip getCurrentTrip() {
        return mRetrievedTrip;
    }

    // *********************** PRIVATE METHODS **************************************************************

    /**
     * Update display of current trip.
     */
    @DebugLog
    private void displayTrip() {

        // Magic of binding
        // Do not use this syntax, it will overwrite activity (we are in a fragment)
        //mBinding = DataBindingUtil.setContentView(getActivity(), R.layout.fragment_trip_detail);
        FragmentTripDetailBinding mBinding = DataBindingUtil.bind(mRootView);
        mBinding.setTrip(mRetrievedTrip);
        mBinding.setTripFormatter(new TripFormatter(getContext()));
        mBinding.executePendingBindings();
    }

    /**
     * Get a human readable and localized name of sorting.
     *
     * @param parNewSortMode sorting mode to provide corresponding string
     * @return a human readable and localized name of sorting.
     */
    private String getReadableName(final SortModes parNewSortMode) {
        String res;
        switch (parNewSortMode) {
            case UNPACKED_FIRST:
                res = getString(R.string.sorting_mode_unpacked_first);
                break;
            case ALPHABETICAL:
                res = getString(R.string.sorting_mode_alphabetical);
                break;
            case CATEGORY:
                res = getString(R.string.sorting_mode_category);
                break;
            case DEFAULT:
            default:
                res = getString(R.string.sorting_mode_default);
                break;
        }
        return res;
    }


    /**
     * Handle click on edit trip button.
     */
    private void onClickEditTrip() {
        ((IMainActivity) getActivity()).openNewTripFragment(mRetrievedTrip.getUUID());
    }

    /**
     * Handle click on "Add item" button.
     * Will add a new item, if not already in the list
     */
    private void onClickAddItem() {
        String tmpStr = mNewItemEditText.getText().toString().trim();

        // checking item not already in the trip list
        if (mRetrievedTrip.alreadyContainsItemOfName(tmpStr)) {
            Toast.makeText(TripDetailFragment.this.getActivity(),
                    getString(R.string.trip_detail__already_existing_item),
                    Toast.LENGTH_LONG).show();
        } else {
            //normal case, addition and refresh of display
            Item newItem = mRetrievedTrip.addItem(tmpStr);
            mIHostingActivity.saveTrip(mRetrievedTrip);
            mNewItemEditText.setText("");
            populateList();
            setHotItem(newItem);
            scrollMyListViewToHotItem();
        }
    }

    /**
     * Handle click on "Add detail item" button.
     * Will add a new item with additional details.
     */
    public final void onClickAddDetailedItem() {
        String tmpStr = mNewItemEditText.getText().toString();
        mNewItemEditText.setText("");
        Item newItem = new Item(mRetrievedTrip, tmpStr);
        ((IMainActivity) getActivity()).openItemDetailFragment(newItem);
        setHotItem(newItem);
    }

    /**
     * Handle click on "Add magic item" button.
     * Will fill item name field.
     */
    public final void onClickAddDMagicItem() {
        // retrieving list of probable items
        if (mProbableItemsList == null) {
            mProbableItemsList = mIHostingActivity.getProbableItemsList();
            mSuggestionIndex = 0;
        }

        // skipping already added
        while (mSuggestionIndex < mProbableItemsList.size()
                && mRetrievedTrip.alreadyContainsItemOfName(mProbableItemsList.get(mSuggestionIndex))) {
            mSuggestionIndex++;
        }

        if (mSuggestionIndex < mProbableItemsList.size()) {
            mNewItemEditText.setText(mProbableItemsList.get(mSuggestionIndex++));
        } else {
            mNewItemEditText.setText("");
            Log.d(TAG, "No more suggestion");
        }
    }

    /**
     * Scroll list view to bottom... so that user can see the just added item.<br>
     *
     * Asked by user snelltheta in issue https://github.com/nbossard/packlist/issues/16
     */
    private void scrollMyListViewToBottom() {
        mItemListView.post(new Runnable() {
            @Override
            public void run() {
                // Select the last row so it will scroll into view...
                mItemListView.smoothScrollToPosition(mListItemAdapter.getCount());
            }
        });
    }

    /**
     * Scroll list view to item of provided name... so that user can see the just added item.<br>
     * Asked by user snelltheta in issue https://github.com/nbossard/packlist/issues/16
     */
    private void scrollMyListViewToHotItem()
    {
        if (mHotItem == null) {
            scrollMyListViewToBottom();
        } else
        {
            mItemListView.post(new Runnable()
            {
                @Override
                public void run()
                {
                    int curPos = searchHotItemPos();
                    mItemListView.smoothScrollToPosition(curPos);
                }
            });
        }
    }

    /**
     * Search position of item in current dispalyed list.
     *
     * @return found position or size-1 if not found.
     */
    private int searchHotItemPos() {
        // searching for item position (various sorting can be used) so full scanning... sic
        Item item;
        int curPos;
        boolean found = false;

        if (mHotItem == null) {
            Log.d(TAG, "searchHotItemPos() No hot item, giving up");
            curPos = mListItemAdapter.getCount() - 1;
        } else
        {
            Log.d(TAG, "searchHotItemPos() Searching for item by UUID" + mHotItem);
            for (curPos = 0; curPos < mListItemAdapter.getCount(); curPos++)
            {
                item = (Item) mListItemAdapter.getItem(curPos);
                if (item.getUUID() == mHotItem.getUUID())
                {
                    Log.d(TAG, "searchHotItemPos() Found item of same UUID at position : " + curPos);
                    found = true;
                    break;
                }
            }
            if (!found)
            {
                Log.d(TAG, "searchHotItemPos() Not found, searching for item by Name" + mHotItem);
                // searching item with similar name
                for (curPos = 0; curPos < mListItemAdapter.getCount(); curPos++)
                {
                    item = (Item) mListItemAdapter.getItem(curPos);
                    if (item.getName().contentEquals(mHotItem.getName()))
                    {
                        Log.d(TAG, "searchHotItemPos() Found item of same name at position : " + curPos);
                        found = true;
                        break;
                    }
                }
            }
        }
        if (!found) {
            Log.d(TAG, "searchHotItemPos() Not found, giving up ");
        }
        Log.d(TAG, "searchHotItemPos() returning " + curPos);
        return curPos;
    }

    /**
     * Effectively delete selected item then refresh the list.
     *
     * @param parPosition position in list of item to be deleted
     */
    private void deleteItemClicked(final int parPosition) {
        Item selectedItem = (Item) mItemListView.getItemAtPosition(parPosition);
        mRetrievedTrip.deleteItem(selectedItem.getUUID());
        mIHostingActivity.saveTrip(mRetrievedTrip);
        mActionMode.finish();
        populateList();
    }

    /**
     * Effectively edit selected item..
     *
     * @param parPosition position in list of item to be edited
     */
    private void editItemClicked(final int parPosition) {
        Item selectedItem = (Item) mItemListView.getItemAtPosition(parPosition);
        setHotItem(selectedItem);
        mIHostingActivity.openItemDetailFragment(selectedItem);
        mActionMode.finish();
    }

    /**
     * Disable the "Add item" button if item text is empty.
     * @param parMButton button to be disabled
     */
    private void disableButtonIfEmptyText(final Button parMButton) {
        EditText newItem = (EditText) mRootView.findViewById(R.id.trip_detail__new_item__edit);
        newItem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(final CharSequence s,
                                          final int start,
                                          final int count,
                                          final int after) {

            }

            @Override
            public void onTextChanged(final CharSequence s,
                                      final int start,
                                      final int before,
                                      final int count) {

            }

            @Override
            public void afterTextChanged(final Editable s) {
                parMButton.setEnabled(s.length() > 0);
            }
        });
    }

    /**
     * Populate list with data in {@link ISavingModule}.
     */
    private void populateList() {
        mItemListView = (ListView) mRootView.findViewById(R.id.trip_detail__list);
        mItemListView.setEmptyView(mRootView.findViewById(R.id.trip_detail__list_empty));
        mListItemAdapter = new ItemAdapter(mRetrievedTrip.getListOfItems(), this.getActivity());
        mListItemAdapter.setSortMode(mRetrievedTrip.getSortMode());
        mListItemAdapter.notifyDataSetChanged();
        mItemListView.setAdapter(mListItemAdapter);
        mItemListView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        mItemListView.setOnItemClickListener(mItemClickListener);
        mItemListView.setOnItemLongClickListener(mLongClickListener);
        mItemListView.invalidate();
        scrollMyListViewToHotItem();
    }

    /**
     * Mark the hot item, the one we want to keep visible in the list, such as when refreshed or reordered.
     * @param parHotItem
     */
    private void setHotItem(Item parHotItem)
    {
        mHotItem = parHotItem;
    }
}
