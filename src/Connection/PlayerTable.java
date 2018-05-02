package Connection;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "rank")
public class PlayerTable implements Serializable{
	
	@DatabaseField(id = true)
	private int id;
	@DatabaseField
	private String name;
	@DatabaseField
	private double score;
	
	public PlayerTable() {
		
	}
	
	public PlayerTable(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public double getScore() {
		return score;
	}
	
	public void setScore(double score) {
		this.score = score;
	}
}
