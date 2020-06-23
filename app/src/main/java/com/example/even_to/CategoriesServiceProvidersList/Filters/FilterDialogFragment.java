package com.example.even_to.CategoriesServiceProvidersList.Filters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.even_to.R;
import com.example.even_to.model.Service;
import com.example.even_to.utils.SubCategoriesArray;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

import static com.example.even_to.R.string.category_drinks;
import static com.example.even_to.R.string.category_food;

public class FilterDialogFragment extends DialogFragment {

    public static final String TAG = "FilterDialog";

    public interface FilterListener {

        void onFilter(Filters filters);

    }

    private View mRootView;

    private Spinner mCategorySpinner;
    private Spinner mCitySpinner;
    private Spinner mSortSpinner;
    private Spinner mCapacitySpinner;
    private Spinner mExperienceSpinner;
    Button mCancel , mApplyFilter;
    private FilterListener mFilterListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.dialog_filters, container, false);

        mCategorySpinner = mRootView.findViewById(R.id.spinner_category);
        mCitySpinner = mRootView.findViewById(R.id.spinner_city);
        mSortSpinner = mRootView.findViewById(R.id.spinner_sort);
        mExperienceSpinner = mRootView.findViewById(R.id.spinner_experience);
        mCapacitySpinner = mRootView.findViewById(R.id.spinner_capacity);

        SubCategoriesArray cat = new SubCategoriesArray(this);
        String category = getArguments().getString("category");

        assert category != null;
        switch (category){

            case "Food" :
                ArrayAdapter foodAdapter = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, cat.getFoodSubCategory());
                mCategorySpinner.setAdapter(foodAdapter);
                break;
                case "Drinks":
                ArrayAdapter drinkAdapter = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, cat.getDrinksSubCategory());
                    mCategorySpinner.setAdapter(drinkAdapter);
                break;
            case "Bakery" :
                ArrayAdapter bakeryAdapter = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, cat.getBakerySubCategory());
                mCategorySpinner.setAdapter(bakeryAdapter);
                break;
            case "Gift":
                ArrayAdapter giftAdapter = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, cat.getGiftSubCategory());
                mCategorySpinner.setAdapter(giftAdapter);
                break;
            case "Decor" :
                ArrayAdapter decorAdapter = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, cat.getDecorSubCategory());
                mCategorySpinner.setAdapter(decorAdapter);
                break;
            case "Photography":
                ArrayAdapter photoAdapter = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, cat.getPhotographySubCategory());
                mCategorySpinner.setAdapter(photoAdapter);
                break;
        }

        mCancel = mRootView.findViewById(R.id.button_cancel);
        mApplyFilter = mRootView.findViewById(R.id.button_search);

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSearchClicked();
            }
        });

        mApplyFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCancelClicked();
            }
        });

        return mRootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof FilterListener) {
            mFilterListener = (FilterListener) context;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getDialog().getWindow().setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public void onSearchClicked() {
        if (mFilterListener != null) {
            mFilterListener.onFilter(getFilters());
        }

        dismiss();
    }

    public void onCancelClicked() {
        dismiss();
    }

    @Nullable
    private String getSelectedCategory() {
        String selected = (String) mCategorySpinner.getSelectedItem();
        if (getString(R.string.value_any_category_food).equals(selected)) {
            return null;
        } else {
            return selected;
        }
    }

    @Nullable
    private String getSelectedCity() {
        String selected = (String) mCitySpinner.getSelectedItem();
        if (getString(R.string.value_any_city).equals(selected)) {
            return null;
        } else {
            return selected;
        }
    }

    private String getSelectedExperience() {
        String selected = (String) mExperienceSpinner.getSelectedItem();
        if (selected.equals(getString(R.string.value_any_experience))) {
            return null;
        } else {
            return selected;
        }
    }

    private String getSelectedCapacity() {
        String selected = (String) mCapacitySpinner.getSelectedItem();
        if (selected.equals(getString(R.string.value_any_capacity))) {
            return null;
        } else {
            return selected;
        }
    }

    @Nullable
    private String getSelectedSortBy() {
        String selected = (String) mSortSpinner.getSelectedItem();
        if (getString(R.string.sort_by_rating).equals(selected)) {
            return Service.KEY_AVG_RATING;
        }
        if (getString(R.string.sort_by_experience).equals(selected)) {
            return Service.KEY_EXPERIENCE;
        }
        if (getString(R.string.sort_by_place).equals(selected)) {
            return Service.KEY_NUMBER_RATINGS;
        }
        if (getString(R.string.sort_by_capacity).equals(selected)) {
            return Service.KEY_CAPACITY;
        }

        return null;
    }

    @Nullable
    private Query.Direction getSortDirection() {
        String selected = (String) mSortSpinner.getSelectedItem();
        if (getString(R.string.sort_by_rating).equals(selected)) {
            return Query.Direction.DESCENDING;
        }
        if (getString(R.string.sort_by_experience).equals(selected)) {
            return Query.Direction.DESCENDING;
        }
        if (getString(R.string.sort_by_capacity).equals(selected)) {
            return Query.Direction.DESCENDING;
        }
        if (getString(R.string.sort_by_place).equals(selected)) {
            return Query.Direction.DESCENDING;
        }

        return null;
    }

    public void resetFilters() {
        if (mRootView != null) {
            mCategorySpinner.setSelection(0);
            mCapacitySpinner.setSelection(0);
            mCitySpinner.setSelection(0);
            mExperienceSpinner.setSelection(0);
            mSortSpinner.setSelection(0);
        }
    }

    public Filters getFilters() {
        Filters filters = new Filters();

        if (mRootView != null) {
            filters.setCategory(getSelectedCategory());
            filters.setCity(getSelectedCity());
            filters.setExperience(getSelectedExperience());
            filters.setCapacity(getSelectedCapacity());
            filters.setSortBy(getSelectedSortBy());
            filters.setSortDirection(getSortDirection());
        }

        return filters;
    }
}
