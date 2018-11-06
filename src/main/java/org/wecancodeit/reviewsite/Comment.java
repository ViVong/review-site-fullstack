package org.wecancodeit.reviewsite;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Comment {
	
	@Id
	@GeneratedValue
	private long id;
	private String user;
	private String comment;
	
	protected Comment() {}
	
	public Comment(String user, String comment) {
		this.user = user;
		this.comment = comment;
	}

	@ManyToMany(mappedBy = "comments")
	private Collection<Review> reviews;

	public long getId() {
		return id;
	}

	public String getUser() {
		return user;
	}

	public String getComment() {
		return comment;
	}

	public Collection<Review> getReviews() {
		return reviews;
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
		Comment other = (Comment) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
