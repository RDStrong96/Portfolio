package com.rollforinitiative.agileplanner.presentation;

import com.rollforinitiative.agileplanner.objects.Magnet;
import com.rollforinitiative.agileplanner.objects.Whiteboard;


//interface so that activity can get callbacks for clicked recycler view items
public interface ItemClickListener {
    void onClick(Whiteboard whiteboard);
}
