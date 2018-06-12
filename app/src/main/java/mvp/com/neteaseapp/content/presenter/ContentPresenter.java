package mvp.com.neteaseapp.content.presenter;

import mvp.com.neteaseapp.content.request.ContentRequest;
import mvp.com.neteaseapp.content.request.ContentRequestCallBack;
import mvp.com.neteaseapp.content.view.ContentViewInterface;

/**
 * Created by wangtao on 2018/6/12.
 */

public class ContentPresenter implements ContentPresenterInter, ContentRequestCallBack {
    private ContentViewInterface mContentViewInterface;

    public ContentPresenter(ContentViewInterface contentViewInterface) {
        mContentViewInterface = contentViewInterface;
    }

    @Override
    public void getNewsRequest() {
        ContentRequest.getNewsRequest(this);
    }

    @Override
    public void getVideoRequest() {
        ContentRequest.getNewsRequest(this);
    }

    @Override
    public void getPicRequest() {
        ContentRequest.getNewsRequest(this);
    }

    @Override
    public void getRequestSuccess() {
        mContentViewInterface.getRequestSuccess();
    }

    @Override
    public void getRequestFail() {
        mContentViewInterface.getRequestFail();
    }
}
