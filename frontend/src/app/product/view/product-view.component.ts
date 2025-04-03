import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ProductEditComponent } from "../edit/product-edit.component";
import { CommonModule } from '@angular/common';
import { ProductService } from '../product-service'; // Import ProductService

@Component({
  selector: 'app-product-view',
  imports: [ProductEditComponent, CommonModule],
  templateUrl: './product-view.component.html',
  styleUrl: './product-view.component.css'
})
export class ProductViewComponent implements OnInit {
  products: any[] = []; // Initialize as an empty array
  product: any;

  constructor(private route: ActivatedRoute, private productService: ProductService) {} // Inject ProductService

  ngOnInit(): void {
    this.productService.getProducts().subscribe((data: any) => {
      this.products = data; // Load products from the service
    });
    this.route.paramMap.subscribe(params => {
      const code = params.get('code');
      if (code) {
        this.productService.getProductByCode(code).subscribe((product: any) => {
          this.product = product; // Load product by code from the service
        });
      }
    });
  }
}
