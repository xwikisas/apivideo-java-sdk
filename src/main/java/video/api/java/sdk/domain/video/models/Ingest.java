package video.api.java.sdk.domain.video.models;

import java.util.ArrayList;
import java.util.List;

public class Ingest {

    public String             status        = "unkown";
    public int                filesize      = 0;
    public List<ReceivedByte> receivedBytes = new ArrayList<ReceivedByte>();

}
