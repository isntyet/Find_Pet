/**
 * Copyright 2017 Harish Sridharan
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ks.hihi.haha.items;

/**
 * Created by sharish on 27/12/16.
 */

public class ItemCard {

    private String title;
    private String place;
    private String feature;
    private String img_std;
    private String key;


    public ItemCard(String title, String place, String feature, String img_std, String key) {
        this.title = title;
        this.place = place;
        this.feature = feature;
        this.img_std = img_std;
        this.key = key;
    }

    public String getPlace() {
        return place;
    }

    public String getFeature() {
        return feature;
    }

    public String getImg_std() {
        return img_std;
    }

    public String getTitle() {
        return title;
    }

    public String getKey(){
        return key;
    }
}
