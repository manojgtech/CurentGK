package com.example.curentgk;

public class Blog {
    private String postId;
    private String blogTitle;


    public Blog(String blogTitle,String postId){
        this.blogTitle=blogTitle;
        this.postId=postId;

    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getBlogTitle() {
        return blogTitle;
    }


    public void setBlogTitle(String blogTitle) {
        this.blogTitle = blogTitle;
    }
}
