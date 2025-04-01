import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class UomService {
  private readonly API_URL = 'http://localhost:8080/uoms/v1';

  constructor(private http: HttpClient) {}

  getUoms() {
    return this.http.get(this.API_URL);
  }

  postUom(uom: any) {
    return this.http.post(this.API_URL, uom);
  }

  getUomByCode(code: string) {
    return this.http.get(`${this.API_URL}/${code}`);
  }
}
