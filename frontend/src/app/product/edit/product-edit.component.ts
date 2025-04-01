import { Component, OnInit, Input } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
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

  constructor(private fb: FormBuilder, private router: Router, private productService: ProductService) {
    this.form = this.fb.group({
      code: ['', [Validators.required, Validators.maxLength(4)]],
      description: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    if (this.product) {
      this.isEditMode = true;
      this.form.patchValue(this.product);
    }
  }

  checkCode(): void {
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
        next: (product) => resolve(!!product), // Resolve true if product exists
        error: () => resolve(false) // Resolve false if product does not exist
      });
    });
  }
}
