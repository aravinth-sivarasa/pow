import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';
import { UomService } from '../uom-service';
import { UomEditComponent } from "../edit/uom-edit.component";

@Component({
  selector: 'app-uom-view',
  imports: [CommonModule, UomEditComponent],
  templateUrl: './uom-view.component.html',
  styleUrl: './uom-view.component.css'
})
export class UomViewComponent implements OnInit {
  uoms: any[] = [];
  uom: any;

  constructor(private route: ActivatedRoute, private uomService: UomService) {}

  ngOnInit(): void {
    this.uomService.getUoms().subscribe((data: any) => {
      this.uoms = data;
    });
    this.route.paramMap.subscribe(params => {
      const code = params.get('code');
      if (code) {
        this.uom = this.uoms.find(u => u.code === code);
      }
    });
  }
}
