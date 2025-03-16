import { NgModule } from '@angular/core';
import { MarkdownModule } from 'ngx-markdown';
import { HttpClient } from '@angular/common/http';

@NgModule({
  imports: [
    MarkdownModule.forRoot({ loader: HttpClient })
  ],
  exports: [MarkdownModule]
})
export class MarkDownModule { }
