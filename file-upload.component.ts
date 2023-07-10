import { Component } from '@angular/core';
import { FileUploadService } from '../file-upload.service';

@Component({
  selector: 'app-file-upload',
  templateUrl: './file-upload.component.html',
  styleUrls: ['./file-upload.component.css']
})
export class FileUploadComponent {


  selectedFile: File | null = null;
  show = false;
  constructor(private fileUploadService: FileUploadService) { }

  onFileSelected(event: any) {
    this.selectedFile = event.target.files[0];
  }

  uploadFile() {
    if (this.selectedFile) {
      this.fileUploadService.uploadFile(this.selectedFile).subscribe(
        (response: any) => {
          console.log('File uploaded successfully.', response);
          if (response.status === 200) {
            this.show = true;
            console.log('Success. Server returned status:', response.status);
          } else {
            console.log('Failed to upload file. Server returned status:', response.status);
          }
        },
        (error: any) => {
          console.log('Failed to upload file.', error);
          this.show = true;
        }
      );
    } else {
      console.log('No file selected.');
    }
  }



  downloadFile() {
    this.fileUploadService.downloadFile();
  }
}
