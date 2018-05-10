package Connection;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "rank")
public class PlayerTable implements Serializable, Comparable<PlayerTable> {

	@DatabaseField(id = true)
	private String name;
	@DatabaseField
	private double score;

	public PlayerTable() {

	}

	public PlayerTable(String name, double score) {
		this.name = name;
		this.score = score;
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

	@Override
	public int compareTo(PlayerTable o) {
		int retval = Double.compare(this.getScore(), o.getScore());
		if (retval > 0)
			return 1;
		if (retval < 0)
			return -1;
		else
			return 0;
	}

}
