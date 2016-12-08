/**
 * Class Song
 * @author David Saeva
 * @version v1.0
 * 
 * Contains all code required to represent a Song drawn from the SongDB file, including all representative parameters (itemcode, description
 * 	artist, album, and price).
 * 
 */

package SongDB;

public class Song {
	
	private String code;
	private String desc;
	private String artist;
	private String album;
	private double price;

	
	/**
	 * Constructor for a Song object
	 * @param data Array containing String values for item code, description, artist, and album
	 * @param price Double representation of the price of this song
	 */
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
