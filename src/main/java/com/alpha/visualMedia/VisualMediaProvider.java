package com.alpha.visualMedia;

import java.util.Set;

public interface VisualMediaProvider
{

    Set<VisualMediaObject> getVisualMediaByDescription(String Description);
}
