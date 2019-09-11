package video.api.java.sdk.domain.video.models;

import java.util.ArrayList;
import java.util.List;

public class Encoding {

    public boolean       playable  = false;
    public List<Quality> qualities = new ArrayList<>();
    public Metadata      metadata  = new Metadata();


}
