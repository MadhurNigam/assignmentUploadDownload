import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { saveAs } from 'file-saver';


@Injectable({
  providedIn: 'root'
})
export class FileUploadService {

  constructor(private http: HttpClient) {}

  uploadFile(file: File) {
    const formData = new FormData();
    formData.append('file', file);
    return this.http.post('http://localhost:8080/upload', formData,{ observe: 'response', responseType: 'text' });
  }
  downloadFile() {
    const filename = 'fileName.jpg'; // Replace with the actual filename and extension
  
    this.http.get('http://localhost:8080/download', { responseType: 'blob' })
      .subscribe(
        (data: Blob) => {
          saveAs(data, filename);
        },
        (error: any) => {
          console.error('Failed to download file.', error);
        }
      );
  }
  
 
}
