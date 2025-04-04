import { Component, OnInit, Input } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router, ActivatedRoute } from '@angular/router';
import { ProductService } from '../product-service';

@Component({
  selector: 'app-product-edit',
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './product-edit.component.html',
  styleUrl: './product-edit.component.css'
})
export class ProductEditComponent implements OnInit {
  @Input() product: any;
  form: FormGroup;
  codeStatus: string = '';
  isCodeAvailable: boolean | null = null;
  isEditMode: boolean = false;

  constructor(private fb: FormBuilder, private router: Router, private productService: ProductService, private route: ActivatedRoute) {
    this.form = this.fb.group({
      code: ['', [Validators.required, Validators.maxLength(4)]],
      description: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    if (this.product) {
      this.isEditMode = true;
      this.form.patchValue(this.product);
    } else {
      const code = this.route.snapshot.paramMap.get('code'); // Get code from URL
      if (code) {
        this.productService.getProductByCode(code).subscribe({
          next: (response) => {
            if (Array.isArray(response) && response.length > 0) {
              this.product = response[0];
              this.isEditMode = true;
              this.form.patchValue(this.product);
            }
          },
          error: (err) => {
            console.error('Error fetching product by code:', err);
          }
        });
      }
    }
  }

  checkCode(): void {
    if (this.isEditMode) {
      this.isCodeAvailable = null;
      this.codeStatus = '';
      return; // Skip code check in edit mode
    }

    const code = this.form.get('code')?.value;
    if (!code) {
      this.isCodeAvailable = null;
      this.codeStatus = '';
      return;
    }
    // Simulate an API call
    // Replace `apiCheckCode` with your actual API logic
    this.apiCheckCode(code).then((exists) => {
      this.isCodeAvailable = !exists;
      this.codeStatus = exists ? 'Code already exists' : 'Code is available';
    });
  }

  submit(): void {
    if (this.form.valid) {
      const productData = this.form.value;
      this.productService.postProduct(productData).subscribe({
        next: () => {
          alert('Data added successfully...');
          this.router.navigate(['/products']); // Navigate to the product list or another page
        },
        error: (err) => {
          console.error('Error adding product:', err);
          alert('Failed to add product.');
        }
      });
    }
  }

  private apiCheckCode(code: string): Promise<boolean> {
    return new Promise((resolve) => {
      this.productService.getProductByCode(code).subscribe({
        next: (response) => resolve(Array.isArray(response) && response.length > 0), // Check if response is an array and not empty
        error: () => resolve(false) // Resolve false if an error occurs
      });
    });
  }
}
