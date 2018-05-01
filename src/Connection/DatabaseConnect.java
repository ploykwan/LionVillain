package Connection;

import java.awt.List;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;fa
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.support.DatabaseConnection;

public class DatabaseConnect {
	private static DatabaseConnect databaseConnect = null;
	private static ConnectionSource connectionSource = null;

	private static ResourceBundle bundle = ResourceBundle.getBundle("");
	private static final String NAME = bundle.getString("jdbc.name");
	private static final String URL = bundle.getString("jdbc.url");
	private Dao<PlayerTable, String> playerDao;
	private java.util.List<PlayerTable> getDetailPlayer;
	private UpdateBuilder<PlayerTable, String> updateBuilder;

	private DatabaseConnect() {
		try {
			connectionSource = new JdbcConnectionSource(URL);
			playerDao = DaoManager.createDao(connectionSource, PlayerTable.class);
		} catch (SQLException e) {
			System.out.println("error");
			e.printStackTrace();
		}
	}

	public static DatabaseConnect getInstance() {
		if( databaseConnect == null )
			databaseConnect = new DatabaseConnect();
		return databaseConnect;
	}
	
	public void closeConnect() throws IOException {
		connectionSource.close();
	}
	
	public java.util.List<PlayerTable> pullAllPlayerdata(){
		try {
			getDetailPlayer = playerDao.queryForAll();
		} catch (SQLException e) {
			System.out.println("pullAllPlayerdata error");
			e.printStackTrace();
		}
		return getDetailPlayer;
	}
	
	public boolean isPlayerExist(String id) {
		PlayerTable playerTable = null;
		try {
			playerTable = playerDao.queryForId(id);
		} catch (SQLException e) {
			System.out.println("pullAllPlayerdata error");
			e.printStackTrace();
		}
		return playerTable != null;
	}
	
	public void createUser(PlayerTable player) {
		try {
			playerDao.createIfNotExists(player);
		}catch (SQLException e) {
			System.out.println("create player error");
			
		}
	}
}