package com.example.even_to.CategoriesServiceProvidersList;

import androidx.lifecycle.ViewModel;

import com.example.even_to.CategoriesServiceProvidersList.Filters.Filters;

public class AllServicesViewModel extends ViewModel {

    private Filters mFilters;

    public AllServicesViewModel() {
        mFilters = Filters.getDefault();
    }

    public Filters getFilters() {
        return mFilters;
    }

    public void setFilters(Filters mFilters) {
        this.mFilters = mFilters;
    }
}
