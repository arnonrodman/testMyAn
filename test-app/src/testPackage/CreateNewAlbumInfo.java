package testPackage;

import java.io.Serializable;

public class CreateNewAlbumInfo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String result,newAlbumName,resultImageURL,albumFolderLocation;	
	
	public CreateNewAlbumInfo(String result, String newAlbumName,String resultImageURL,String albumFolderLocation) {		
		this.result = result;
		this.newAlbumName = newAlbumName;
		this.resultImageURL = resultImageURL;
		this.albumFolderLocation = albumFolderLocation;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getNewAlbumName() {
		return newAlbumName;
	}
	public void setNewAlbumName(String newAlbumName) {
		this.newAlbumName = newAlbumName;
	}
	public String getResultImageURL() {
		return resultImageURL;
	}
	public void setResultImageURL(String resultImageURL) {
		this.resultImageURL = resultImageURL;
	}
	public String getAlbumFolderLocation() {
		return albumFolderLocation;
	}
	public void setAlbumFolderLocation(String albumFolderLocation) {
		this.albumFolderLocation = albumFolderLocation;
	}
	
}
