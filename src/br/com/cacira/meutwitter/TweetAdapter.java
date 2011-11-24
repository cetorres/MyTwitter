package br.com.cacira.meutwitter;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

public class TweetAdapter extends BaseAdapter {

	List<Tweet> tweetList;
	LayoutInflater inflater;
	
	public TweetAdapter(List<Tweet> tweets) {
		tweetList = tweets;
		notifyDataSetChanged();
	}
	
	protected static class ViewHolder {
		TextView text;
		ImageView image;
	}
	
	@Override
	public int getCount() {
		return tweetList == null ? 0 : tweetList.size();
	}

	@Override
	public Tweet getItem(int position) {
		return tweetList.get(position);
	}
	
	@Override
	public long getItemId(int position) {
		return position;
	}
	
	public void add(final Tweet tweet) {
		tweetList.add(tweet);
		notifyDataSetChanged();
	}
	
	public void updateList(final List<Tweet> tweets) {
		tweetList = tweets;
		notifyDataSetChanged();
	}
	
	public void clear() {
		tweetList.clear();
		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.list_cell, null);
			holder = new ViewHolder();
			holder.text = (TextView) convertView.findViewById(R.id.textTweet);
			holder.image = (ImageView) convertView.findViewById(R.id.imageTweet);
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		Tweet tweet = getItem(position);
		
		// Obter imagens de forma assíncrona (mais rápido e sem travar a interface)
		String imageUrl = tweet.getUserImage();
		if (imageUrl != null) {
			holder.image.setTag(imageUrl);  
	        BitmapManager.INSTANCE.loadBitmap(imageUrl, holder.image, 32, 32);  
		}
		
		// Obter imagens de forma síncrona (trava muito a interface)
		/*
		String imageUrl = tweet.getUserImage();
		Bitmap bitmap = null;
		if (imageUrl != null) {
			URL newurl = null;
			try {
				newurl = new URL(imageUrl);
				bitmap = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}  
			holder.image.setImageBitmap(bitmap);
		}
		*/
		
		// Monta texto da visualização do tweet, jutando o usuário, texto e data de postagem
		Spanned message = Html.fromHtml("<b><font color='#0E8999'>" 
				+ tweet.getUserScreenName() + "</font></b><br/>" 
				+ tweet.getText() + "<br/><small><font color='#CCCCCC'>" 
				+ tweet.getDateTime() + "</font></small>");
		holder.text.setText(message);
		
		return convertView;
	}

}
