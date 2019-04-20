package com.example.worldapp.Models;

import com.google.firebase.auth.FirebaseUser;

import java.util.UUID;

public class BookingManager {
    private FirebaseUser mBuyer;
    private FirebaseUser mSeller;
    private UUID mBuyIdList;
    private double mPrice;
    private int mFee;
    private int mTotalPrice;
}
