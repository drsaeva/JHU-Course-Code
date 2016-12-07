package songDB;

public class Song {
	
	private String code;
	private String desc;
	private String artist;
	private String album;
	private double price;

	public Song() {
		this.code = "";
		this.desc = "";
		this.artist = "";
		this.album = "";
		this.price = 0.0;
	}

	public Song(String[] data, double price) {
		this.code = data[0];
		this.desc = data[1];
		this.artist = data[2];
		this.album = data[3];
		this.price = price;
	}

	public String getItemCode() {
		return code;
	}

	public void setItemCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price){
		this.price = price;
	}

	public String toString() {
		return desc;
	}
}
