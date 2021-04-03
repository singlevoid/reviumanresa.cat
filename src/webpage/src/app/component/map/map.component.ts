import { ElementRef, ViewChild } from '@angular/core';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { environment } from 'src/environments/environment';
import { DataService } from '../../service/data.service';
import { HostListener } from '@angular/core';
import { AngularFireStorage } from '@angular/fire/storage';
import { Photograph } from 'src/app/model/photograph';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.scss']
})
export class MapComponent {


  @ViewChild('hoverContainer',{static:false}) hoverContainer: ElementRef
  @ViewChild('hoverImage',{static:false}) hoverImage: ElementRef


  map = {
    lat: 41.7242344,
    lng: 1.8235865,
    zoom: 15.81,
    styles: environment.mapStyle,
  }


  private markerHover: Photograph;

  private mouseX: number;
  private mouseY: number;
  private imgWidth: number = 0;
  private imgHeight: number = 0;


  constructor(public data: DataService,
              private router: Router,
              public storage: AngularFireStorage) { }



  @HostListener('mousemove', ['$event']) onMouseMove(event: { clientX: number; clientY: number; }) {
    this.mouseX = event.clientX;
    this.mouseY = event.clientY;
  }


  public getMarkers(){
    var markers = []

    this.data.getPhotographs().forEach(
      photo =>{
        if(photo.isGeolocalized()){
          markers.push(photo)
        }
      });
    return markers
  }


  public getCurrentMarker(){
    return this.markerHover;
  }


  public markerEnter(photograph: Photograph){
    this.markerHover = photograph;
  }


  public markerLeave(){
    this.markerHover = null;
    this.imgWidth = 0;
    this.imgHeight = 2000;
  }


  public markerClicked(id: string): void{
    console.log("clicked:" + id)
    this.router.navigate(['photo', id]);
  }


  public onImageLoad(){
    this.imgWidth = this.hoverImage.nativeElement.width
    this.imgHeight = this.hoverImage.nativeElement.height
  }


  public getThumbnailHeigth(): number{
      return this.mouseY - this.imgHeight - 30
  }


  public getThumbnailWidth(): number{
    return this.mouseX - Math.round(this.imgWidth / 2)
  }


  public thumbnail(): string{
    return this.markerHover.getLowQuality(this.storage)
  }

}
