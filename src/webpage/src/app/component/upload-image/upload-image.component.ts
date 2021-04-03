import { Component, ElementRef, Input, OnInit, ViewChild } from '@angular/core';
import { NgxImageCompressService } from 'ngx-image-compress';

@Component({
  selector: 'app-upload-image',
  templateUrl: './upload-image.component.html',
  styleUrls: ['./upload-image.component.scss']
})
export class UploadImageComponent implements OnInit{


  @ViewChild('highQualityImage') highQualityImage: ElementRef;
  @ViewChild('lowQualityImage') lowQualityImage: ElementRef;

  @Input() private highQualitySource: string;
  @Input() private lowQualitySource: string;

  private lowImageSize: string;
  private highImageSize: string;
  private userUploadImage: boolean = false;

  constructor(private imageCompress: NgxImageCompressService) { }


  public getHighQualitySource(): string   { return this.highQualitySource }
  public getLowQualitySource(): string    { return this.lowQualitySource }
  public getHighImageSize(): string       { return this.highImageSize }
  public getLowImageSize(): string        { return this.lowImageSize }

  ngOnInit(): void {}


  public calculateImageSize(): void{
    this.calculateHighImageSize()
    this.calculateLowImageSize()
  }


  public uploadImage(): void{
    this.userUploadImage = true;
    this.imageCompress.uploadFile().then(({image, orientation}) =>{
      this.highQualitySource = image;

      this.imageCompress.compressFile(image, orientation, 33, 100).then(
        result => {this.lowQualitySource = result}
      );
    })
  }


  public calculateHighImageSize(): void{
    if(this.highQualityImage?.nativeElement?.width){
      this.highImageSize = `${this.highQualityImage?.nativeElement.naturalWidth} x ${this.highQualityImage?.nativeElement.naturalHeight}`
    }
  }


  public calculateLowImageSize(): void{
    if(this.lowQualityImage?.nativeElement?.width){
      this.lowImageSize = `${this.lowQualityImage?.nativeElement.naturalWidth} x ${this.lowQualityImage?.nativeElement.naturalHeight}`
    }
  }


  public getLowFileSize(): string{
    var size = this.imageCompress.byteCount(this.getLowQualitySource())
    size = size / 1024
    if(size < 1024){
      return `${Math.round((size + Number.EPSILON) * 100) / 100} KB`
    }
    else{
      size = size / 1024
      return `${Math.round((size + Number.EPSILON) * 100) / 100} MB`
    }
  }


  public getHighFileSize(): string{
    var size = this.imageCompress.byteCount(this.getHighQualitySource())
    size = size / 1024
    if(size < 1024){
      return `${Math.round((size + Number.EPSILON) * 100) / 100} KB`
    }
    else{
      size = size / 1024
      return `${Math.round((size + Number.EPSILON) * 100) / 100} MB`
    }
  }


  public async getHighQuality(): Promise<Blob>{
    if(!this.userUploadImage) {return}
    try{
    var blob: Blob;
    blob = await fetch(this.highQualitySource).then(res => res.blob())
    return blob;
    }
    catch (e){
      return;
    }
  }


  public async getLowQuality(): Promise<Blob>{
    if(!this.userUploadImage) {return}
    try{
    var blob: Blob;
    blob = await fetch(this.lowQualitySource).then(res => res.blob())
    return blob;
    }
    catch (e){
      return
    }
  }

}
