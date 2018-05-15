package Connection;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.support.ConnectionSource;
/**
 * Create and update the data in database.
 * @author Kwankaew
 *
 */
public class DatabaseConnect {
	private static DatabaseConnect databaseConnect = null;
	private static ConnectionSource connectionSource = null;

	private static ResourceBundle bundle = ResourceBundle.getBundle("config");
	private static final String NAME = bundle.getString("jdbc.username");
	private static final String URL = bundle.getString("jdbc.url");
	private static final String PW = bundle.getString("jdbc.password");
	private Dao<PlayerTable, String> playerDao;
	private java.util.List<PlayerTable> getDetailPlayer;
	private UpdateBuilder<PlayerTable, String> updateBuilder;

	private DatabaseConnect() {
		try {
			connectionSource = new JdbcConnectionSource(URL,NAME,PW);
			playerDao = DaoManager.createDao(connectionSource, PlayerTable.class);
			updateBuilder = playerDao.updateBuilder();
		} catch (SQLException e) {
			System.out.println("error");
		}
	}

	public static DatabaseConnect getInstance() {
//		System.out.println(NAME + "<<<>>>" + URL);
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
		}
		return getDetailPlayer;
	}
	
	public void createUser(PlayerTable player) {
		try {
			playerDao.createIfNotExists(player);
		}catch (SQLException e) {
			System.out.println("create player error");
		}
	}
	
	public void update(PlayerTable player) {
			try {
//				System.out.println("update time: "+ player.getScore()+", "+player.getName());
				updateBuilder.where().eq("name", player.getName());
				updateBuilder.updateColumnValue("score", player.getScore());
				updateBuilder.update();
			} catch (SQLException e) {
				System.out.println("Update score error.");
			}
	
	}
}
