package org.wecancodeit.reviewsite;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;

@Entity
public class Review {

    @Id
    @GeneratedValue
    private long id;
	private int month;
	private int date;
	private String title;
	private String url;
	@Lob
	private String content;
	
	@ManyToMany
	private Collection<Category> categories;

    protected Review() {}

    public Review(int month, int date, String title, String url, String content, Category...categories) {
		this.month = month;
		this.date = date;
		this.title = title;
		this.url = url;
		this.content = content;
		this.categories = new HashSet<>(Arrays.asList(categories));
	}

	public String getDate() {
		return month + "/" + date;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getUrl() {
		return url;
	}
	
	public String getContent() {
		return content;
	}
	
	public long getId() {
		return id;
	}
	
	public Collection<Category> getCategories() {
		return categories;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Review other = (Review) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
}