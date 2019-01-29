package com.internousdev.gerbera.action;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.internousdev.gerbera.dao.UserInfoDAO;
import com.opensymphony.xwork2.ActionSupport;

public class LogoutAction extends ActionSupport implements SessionAware{
	private Map<String,Object> session;
	public String execute(){

		//一定時間操作をしない場合セッションアウトする
		if(!session.containsKey("mCategoryList")){
			return "sessionTimeOut";
		}
		UserInfoDAO userInfoDao = new UserInfoDAO();
		String loginId = String.valueOf(session.get("loginId"));
		boolean savedLoginId = Boolean.valueOf(String.valueOf(session.get("savedLoginId")));
		int count =userInfoDao.logout(loginId);

		//ログアウトする際にログインされた情報を空にする
		if(count > 0){
			session.clear();

		//ログイン時にログインID保存にチェックした場合ログインされた情報を保持する
			if(savedLoginId){
				session.put("savedLoginId", savedLoginId);
				session.put("lastLoginId", loginId);
			}
		}
		return SUCCESS;
	}

	public Map<String,Object>getSession(){
		return session;
	}
	public void setSession(Map<String,Object> session){
		this.session = session;
	}

}
