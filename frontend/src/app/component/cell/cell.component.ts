import {Component, Input, numberAttribute, OnInit} from '@angular/core';
import {CommonModule} from '@angular/common';

@Component({
  selector: 'app-cell',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './cell.component.html',
  styleUrl: './cell.component.css'
})
export class CellComponent implements OnInit {
  @Input({required: true, transform: numberAttribute}) position!: number
  classes: Record<string, boolean> = {}

  updateClasses() {
    this.classes = {
      'border-top-0': [0, 1, 2].includes(this.position),
      'border-bottom-0': [6, 7, 8].includes(this.position),
      'border-start-0': [0, 3, 6].includes(this.position),
      'border-end-0': [2, 5, 8].includes(this.position)
    }
  }

  ngOnInit(): void {
    this.updateClasses()
  }

}
