import { Component, OnInit, Input } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { UomService } from '../uom-service';

@Component({
  selector: 'app-uom-edit',
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './uom-edit.component.html',
  styleUrl: './uom-edit.component.css'
})
export class UomEditComponent implements OnInit {
  @Input() uom: any;
  form: FormGroup;
  isEditMode: boolean = false;
  isCodeAvailable: boolean | null = null;
  codeStatus: string = '';

  constructor(private fb: FormBuilder, private router: Router, private uomService: UomService) {
    this.form = this.fb.group({
      code: ['', [Validators.required, Validators.maxLength(4)]],
      description: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    if (this.uom) {
      this.isEditMode = true;
      this.form.patchValue(this.uom);
    }
  }

  checkCode(): void {
    const code = this.form.get('code')?.value;
    if (!code) {
      this.isCodeAvailable = null;
      this.codeStatus = '';
      return;
    }
    this.apiCheckCode(code).then((exists) => {
      this.isCodeAvailable = !exists;
      this.codeStatus = exists ? 'Code already exists' : 'Code is available';
    });
  }

  private apiCheckCode(code: string): Promise<boolean> {
    return new Promise((resolve) => {
      this.uomService.getUomByCode(code).subscribe({
        next: (response) => resolve(Array.isArray(response) && response.length > 0), 
        error: () => resolve(false) // Resolve false if an error occurs
      });
    });
  }

  submit(): void {
    if (this.form.valid) {
      const uomData = this.form.value;
      this.uomService.postUom(uomData).subscribe({
        next: () => {
          alert('Data added successfully...');
          this.router.navigate(['/uoms']);
        },
        error: (err) => {
          console.error('Error adding uom:', err);
          alert('Failed to add uom.');
        }
      });
    }
  }
}
