import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class ProductService {
  private readonly API_URL = '/api/products';

  constructor(private http: HttpClient) {}

  getProducts() {
    return this.http.get(this.API_URL);
  }

  postProduct(product: any) {
    return this.http.post(this.API_URL, product); 
  }

  getProductByCode(code: string) {
    return this.http.get(this.API_URL+"/"+code); 
  }
}
