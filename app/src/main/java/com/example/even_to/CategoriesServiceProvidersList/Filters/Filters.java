package com.example.even_to.CategoriesServiceProvidersList.Filters;

import android.content.Context;
import android.text.TextUtils;

import com.example.even_to.R;
import com.example.even_to.model.Service;
import com.google.firebase.firestore.Query;

public class Filters {
    private String type = null;
    private String city = null;
    private String experience = null;
    private String capacity = null;
    private String sortBy = null;
    private Query.Direction sortDirection = null;

    public Filters() {
    }


    public static Filters getDefault() {
        Filters filters = new Filters();
        filters.setSortBy(Service.KEY_AVG_RATING);
        filters.setSortDirection(Query.Direction.DESCENDING);

        return filters;
    }

    public boolean hasType() {
        return !(TextUtils.isEmpty(type));
    }

    public boolean hasCity() {
        return !(TextUtils.isEmpty(city));
    }

    public boolean hasExperience() {
        return !(TextUtils.isEmpty(experience));
    }

    public boolean hasCapacity() {
        return !(TextUtils.isEmpty(capacity));
    }

    public boolean hasSortBy() {
        return !(TextUtils.isEmpty(sortBy));
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }


    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }


    public Query.Direction getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(Query.Direction sortDirection) {
        this.sortDirection = sortDirection;
    }

    public String getSearchDescription(Context context) {
        StringBuilder desc = new StringBuilder();

        if (type == null && city == null) {
            desc.append("<b>");
            desc.append(context.getString(R.string.all_services));
            desc.append("</b>");
        }

        if (type != null) {
            desc.append("<b>");
            desc.append(type);
            desc.append("</b>");
        }

        if (type != null && city != null) {
            desc.append(" in ");
        }

        if (city != null) {
            desc.append("<b>");
            desc.append(city);
            desc.append("</b>");
        }
//        if (type != null && city != null && experience != null) {
//            desc.append(" has ");
//        }
//        if (experience != null) {
//            desc.append(" for ");
//            desc.append("<b>");
//            desc.append(experience + " exp");
//            desc.append("</b>");
//        }
//        if (type != null && city != null && experience != null && capacity != null) {
//            desc.append(" of ");
//        }
//        if (capacity != null) {
//            desc.append(" for ");
//            desc.append("<b>");
//            desc.append(capacity + " size");
//            desc.append("</b>");
//        }

        return desc.toString();
    }

    public String getOrderDescription(Context context) {
        if (Service.KEY_EXPERIENCE.equals(sortBy)) {
            return context.getString(R.string.sorted_by_experience);
        } else if (Service.KEY_NUMBER_RATINGS.equals(sortBy)) {
            return context.getString(R.string.sorted_by_place);
        } else if (Service.KEY_CAPACITY.equals(sortBy)) {
            return context.getString(R.string.sorted_by_capacity);
        } else {
            return context.getString(R.string.sorted_by_rating);
        }
    }
}

