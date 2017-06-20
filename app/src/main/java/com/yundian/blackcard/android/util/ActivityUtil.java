package com.yundian.blackcard.android.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.yundian.blackcard.android.R;
import com.yundian.blackcard.android.activity.ArticleCommentActivity;
import com.yundian.blackcard.android.activity.ArticleDetailActivity;
import com.yundian.blackcard.android.activity.DynamicAddActivity;
import com.yundian.blackcard.android.activity.DynamicCommentActivity;
import com.yundian.blackcard.android.activity.DynamicDetailActivity;
import com.yundian.blackcard.android.activity.ShowBigImageActivity;
import com.yundian.blackcard.android.constant.ActionConstant;
import com.yundian.blackcard.android.model.ArticleModel;
import com.yundian.blackcard.android.model.DynamicModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2017-06-18 15:04
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class ActivityUtil {

    public static void next(Context context, Class clazz) {
        Intent intent = new Intent(context, clazz);
        context.startActivity(intent);
    }


    public static void nextDynamicDetail(Context context, DynamicModel dynamicModel) {
        Intent intent = new Intent(context, DynamicDetailActivity.class);
        intent.putExtra(ActionConstant.IntentKey.DYNAMIC, dynamicModel);
        context.startActivity(intent);
    }

    public static void nextDynamicAdd(Activity activity) {
        Intent intent = new Intent(activity, DynamicAddActivity.class);
        activity.startActivityForResult(intent, ActionConstant.Action.DYNAMIC_RELEASE_REQUEST);
    }

    public static void nextArticleDetail(Context context, ArticleModel articleModel) {
        Intent intent = new Intent(context, ArticleDetailActivity.class);
        intent.putExtra(ActionConstant.IntentKey.ARTICLE, articleModel);
        context.startActivity(intent);
    }

    public static void nextBigImg(Context context, List<String> images, int position) {
        Intent intent = new Intent(context, ShowBigImageActivity.class);
        intent.putStringArrayListExtra(ActionConstant.IntentKey.IMGS_LIST, (ArrayList<String>) images);
        intent.putExtra(ActionConstant.IntentKey.IMGS_POSITION, position);
        ((Activity) context).startActivityForResult(intent, ActionConstant.Action.BIG_IMAGE);
        ((Activity) context).overridePendingTransition(R.anim.alpha_in, R.anim.alpha_stay);
    }

    public static void nextArticleComment(Activity activity, ArticleModel articleModel) {
        Intent intent = new Intent(activity, ArticleCommentActivity.class);
        intent.putExtra(ActionConstant.IntentKey.ARTICLE, articleModel);
        activity.startActivityForResult(intent, ActionConstant.Action.ARTICLE_COMMENT_REQUEST);
    }


    public static void nextDynamicComment(Activity activity, DynamicModel dynamicModel) {
        Intent intent = new Intent(activity, DynamicCommentActivity.class);
        intent.putExtra(ActionConstant.IntentKey.DYNAMIC, dynamicModel);
        activity.startActivityForResult(intent, ActionConstant.Action.DYNAMIC_COMMENT_REQUEST);
    }
}
