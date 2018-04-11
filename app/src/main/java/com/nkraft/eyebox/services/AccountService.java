package com.nkraft.eyebox.services;

import com.nkraft.eyebox.models.User;
import com.nkraft.eyebox.utils.Debug;
import com.nkraft.eyebox.utils.HttpUtil;

import org.json.JSONArray;
import org.json.JSONObject;

public class AccountService extends BaseService {

    private static AccountService _instance;

    public static AccountService instance() {
        if (_instance == null) {
            _instance = new AccountService();
        }
        return _instance;
    }

    public User currentUser;

    public PagedResult<User> login(String userName, String password) {
        try {
            String response = get("eyebox/api/login.php",
                    new HttpUtil.KeyValue("key", API_KEY),
                    new HttpUtil.KeyValue("username", userName),
                    new HttpUtil.KeyValue("password", password));

            if (response.contains("INVALID")) {
                return new PagedResult<>(response);
            }

            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("employees");
            JSONObject userDummy = jsonArray.getJSONObject(0);
            long id = userDummy.getLong("idcode");
            int assignedBranch = userDummy.getInt("branchlink");
            String nick = userDummy.getString("nick");
            String name = userDummy.getString("namex");
            User user = new User(id);
            user.setAssignedBranch(assignedBranch);
            user.setUserName(nick);
            user.setName(name);
            currentUser = user;
            return new PagedResult<>(user, 1);
        } catch (Exception e) {
            Debug.log("error: %s", e.getMessage());
            return new PagedResult<>(e.getMessage());
        }
    }
}
