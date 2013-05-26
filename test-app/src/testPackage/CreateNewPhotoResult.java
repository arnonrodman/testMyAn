package testPackage;

import java.io.Serializable;

public class CreateNewPhotoResult implements Serializable{

	private String result,localUserAlbumFolde,image;

	public CreateNewPhotoResult(String result,String localUserAlbumFolde,String image) {
		super();
		this.result = result;
		this.localUserAlbumFolde = localUserAlbumFolde;
		this.image = image;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getLocalUserAlbumFolde() {
		return localUserAlbumFolde;
	}

	public void setLocalUserAlbumFolde(String localUserAlbumFolde) {
		this.localUserAlbumFolde = localUserAlbumFolde;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	
}
