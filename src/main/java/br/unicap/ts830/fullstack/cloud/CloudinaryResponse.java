package br.unicap.ts830.fullstack.cloud;

import java.util.List;

/**
 *
 * @author shido
 */
public class CloudinaryResponse {
    private String public_id;
    private int version;
    private String signature;
    private int width;
    private int height;
    private String format;
    private String resource_type;
    private String created_at;
    private List tags;
    private int bytes;
    private String type;
    private String etag;
    private boolean placeholder;
    private String url;
    private  String secure_url;
    private String original_filename;
    
    public CloudinaryResponse() {}

    /**
     * @return the public_id
     */
    public String getPublic_id() {
        return public_id;
    }

    /**
     * @param public_id the public_id to set
     */
    public void setPublic_id(String public_id) {
        this.public_id = public_id;
    }

    /**
     * @return the version
     */
    public int getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(int version) {
        this.version = version;
    }

    /**
     * @return the signature
     */
    public String getSignature() {
        return signature;
    }

    /**
     * @param signature the signature to set
     */
    public void setSignature(String signature) {
        this.signature = signature;
    }

    /**
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * @param width the width to set
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * @param height the height to set
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * @return the format
     */
    public String getFormat() {
        return format;
    }

    /**
     * @param format the format to set
     */
    public void setFormat(String format) {
        this.format = format;
    }

    /**
     * @return the resource_type
     */
    public String getResource_type() {
        return resource_type;
    }

    /**
     * @param resource_type the resource_type to set
     */
    public void setResource_type(String resource_type) {
        this.resource_type = resource_type;
    }

    /**
     * @return the created_at
     */
    public String getCreated_at() {
        return created_at;
    }

    /**
     * @param created_at the created_at to set
     */
    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    /**
     * @return the tags
     */
    public List getTags() {
        return tags;
    }

    /**
     * @param tags the tags to set
     */
    public void setTags(List tags) {
        this.tags = tags;
    }

    /**
     * @return the bytes
     */
    public int getBytes() {
        return bytes;
    }

    /**
     * @param bytes the bytes to set
     */
    public void setBytes(int bytes) {
        this.bytes = bytes;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the etag
     */
    public String getEtag() {
        return etag;
    }

    /**
     * @param etag the etag to set
     */
    public void setEtag(String etag) {
        this.etag = etag;
    }

    /**
     * @return the placeholder
     */
    public boolean isPlaceholder() {
        return placeholder;
    }

    /**
     * @param placeholder the placeholder to set
     */
    public void setPlaceholder(boolean placeholder) {
        this.placeholder = placeholder;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the secure_url
     */
    public String getSecure_url() {
        return secure_url;
    }

    /**
     * @param secure_url the secure_url to set
     */
    public void setSecure_url(String secure_url) {
        this.secure_url = secure_url;
    }

    /**
     * @return the original_filename
     */
    public String getOriginal_filename() {
        return original_filename;
    }

    /**
     * @param original_filename the original_filename to set
     */
    public void setOriginal_filename(String original_filename) {
        this.original_filename = original_filename;
    }
}

/*
{
"public_id": "yxxzudhfnw7pk4vxyyx3",
"version": 1513444188,
"signature": "9a44dc9c87425fd4ce6daa6500b73e3cad25911e",
"width": 1080,
"height": 400,
"format": "jpg",
"resource_type": "image",
"created_at": "2017-12-16T17:09:48Z",
"tags": [],
"bytes": 172224,
"type": "upload",
"etag": "48b380bc781776d7a98363856676098e",
"placeholder": false,
"url": "http://res.cloudinary.com/shido/image/upload/v1513444188/yxxzudhfnw7pk4vxyyx3.jpg",
"secure_url": "https://res.cloudinary.com/shido/image/upload/v1513444188/yxxzudhfnw7pk4vxyyx3.jpg",
"original_filename": "shido_ana_sign"
}
*/
