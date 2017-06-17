package com.yundian.blackcard.android.fragment.base;


import com.yundian.blackcard.android.fragment.BaseFragment;
import com.yundian.comm.controller.BaseController;
import com.yundian.comm.manager.IifecycleManage;
import com.yundian.comm.util.StringUtils;

import butterknife.ButterKnife;

/**
 * 可注册多控制器Fragment
 * Created by yaowang on 16/3/31.
 */
public abstract class BaseControllerFragment extends BaseFragment {
    protected IifecycleManage controllers = new IifecycleManage();

    /**
     * 注册控制器
     *
     * @param key        控制器key
     * @param controller
     * @param isInject   是否注册 view 或 事件
     */
    protected void registerController(String key, BaseController controller, boolean isInject) {
        if (!StringUtils.isEmpty(key) && controller != null) {
            controllers.register(key, controller);
            if (isInject) {
                ButterKnife.bind(controller,rootView);
            }
        }
    }

    protected  void registerController(BaseController controller,boolean isInject) {
        if( controller != null )
        {
            registerController(controller.getClass().getSimpleName(),controller,isInject);
        }
    }

    /**
     * 获取注册的控制器
     *
     * @param key
     * @return
     */
    public <Controller extends BaseController> Controller getController(String key) {
        return (Controller) controllers.get(key);
    }

    @Override
    public void onInit() {
        super.onInit();
        onRegisterController();
    }

    /**
     * 开始注册控制器
     */
    protected void onRegisterController() {

    }


    @Override
    public void initView() {
        super.initView();
        controllers.initView();
    }

    @Override
    public void initListener() {
        super.initListener();
        controllers.initListener();
    }

    @Override
    public void initData() {
        super.initData();
        controllers.initData();

    }

    @Override
    public void onPause() {
        super.onPause();
        controllers.onPause();

    }

    @Override
    public void onStart() {
        super.onStart();
        controllers.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();
        controllers.onStop();

    }

    @Override
    public void onResume() {
        super.onResume();
        controllers.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        controllers.onDestroy();
    }
}
