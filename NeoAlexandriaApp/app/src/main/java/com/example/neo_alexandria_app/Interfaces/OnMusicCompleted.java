package com.example.neo_alexandria_app.Interfaces;

import com.example.neo_alexandria_app.DataModels.Song;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public interface OnMusicCompleted {
    public void musicTaskCompleted(List<Song> songs, SweetAlertDialog dialog);
}
