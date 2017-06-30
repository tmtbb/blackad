package com.yundian.blackcard.android.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.yundian.blackcard.android.R;
import com.yundian.blackcard.android.activity.ArticleCommentActivity;
import com.yundian.blackcard.android.activity.ArticleDetailActivity;
import com.yundian.blackcard.android.activity.DynamicAddActivity;
import com.yundian.blackcard.android.activity.DynamicCommentActivity;
import com.yundian.blackcard.android.activity.DynamicDetailActivity;
import com.yundian.blackcard.android.activity.LoginActivity;
import com.yundian.blackcard.android.activity.RegisterActivity;
import com.yundian.blackcard.android.activity.ShowBigImageActivity;
import com.yundian.blackcard.android.activity.TribeAddActivity;
import com.yundian.blackcard.android.activity.TribeApplyActivity;
import com.yundian.blackcard.android.activity.TribeDetailActivity;
import com.yundian.blackcard.android.activity.TribeInviteActivity;
import com.yundian.blackcard.android.activity.UserSetInfoActivity;
import com.yundian.blackcard.android.activity.WebViewActivity;
import com.yundian.blackcard.android.constant.ActionConstant;
import com.yundian.blackcard.android.model.ArticleModel;
import com.yundian.blackcard.android.model.DynamicModel;
import com.yundian.comm.util.SPUtils;

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

    public static void nextDynamicAdd(Activity activity, String circleId) {
        Intent intent = new Intent(activity, DynamicAddActivity.class);
        intent.putExtra(ActionConstant.IntentKey.TRIBEID_ID, circleId);
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

    public static void nextTribeAdd(Activity context) {
        Intent intent = new Intent(context, TribeAddActivity.class);
        context.startActivityForResult(intent, ActionConstant.Action.TRIBE_ADD_REQUEST);
    }

    public static void nextTribeInvite(Context context) {
        next(context, TribeInviteActivity.class);
    }

    public static void nextTribeDetail(Context context, String tribeId) {
        Intent intent = new Intent(context, TribeDetailActivity.class);
        intent.putExtra(ActionConstant.IntentKey.TRIBEID_ID, tribeId);
        context.startActivity(intent);
    }

    public static void nextTribeApplay(Activity context, String tribeId, int identity) {
        Intent intent = new Intent(context, TribeApplyActivity.class);
        intent.putExtra(ActionConstant.IntentKey.TRIBEID_ID, tribeId);
        intent.putExtra(ActionConstant.IntentKey.TRIBEID_IDENTITY, identity);
        context.startActivityForResult(intent, ActionConstant.Action.TRIBE_APPLY_REQUEST);
    }

    public static void nextWebView(Context context, String url) {
        Intent intent = new Intent();
        intent.setData(Uri.parse(url));
        intent.setClass(context, WebViewActivity.class);
        context.startActivity(intent);
    }

    public static void nextLoginAndClearToken(Context context) {
        SPUtils.remove(context, "UserToken");
        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
