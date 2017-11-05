package com.bruce.kidzymer.kidpoems;

/**
 * Created by bruce on 19/02/2017.
 */

public class Poem {

    //splahs add chesava?
    //chesa error vachindani remove chesesa nly pic uncha
    //enkem chesava splash add chesi and enka am chesav?
    //no splash activity lo teskunna adi launcher activity ante select chesa
    //k
    //creating model class for poem, just contains a name.
    private String poem;
    //send apk...

    public Poem(String poem) {
        this.poem = poem;
    }

    //connect device, run ienda?ha mri error undi?
    //studio restart chestha agu

    public String getPoem() {
        return poem;
    }

    public void setPoem(String poem) {
        this.poem = poem;
    }
}
